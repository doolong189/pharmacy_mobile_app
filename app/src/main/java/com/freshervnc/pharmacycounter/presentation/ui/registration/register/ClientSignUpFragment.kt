package com.freshervnc.pharmacycounter.presentation.ui.registration.register

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.DialogChooseCameraOrGalleryBinding
import com.freshervnc.pharmacycounter.databinding.FragmentClientSignUpBinding
import com.freshervnc.pharmacycounter.domain.models.Agency
import com.freshervnc.pharmacycounter.presentation.ui.registration.register.viewmodel.RegisterViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.viewmodel.AgencyViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.viewmodel.ProvinceViewModel
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class ClientSignUpFragment : Fragment() {
    private lateinit var binding: FragmentClientSignUpBinding
    private lateinit var provincesViewModel: ProvinceViewModel
    private lateinit var agencyViewModel: AgencyViewModel
    private lateinit var clientViewModel: RegisterViewModel
    private var itrProvinces: Int = 1
    private var itrAgency: Int = 1
    private val CAMERA_REQUEST_CODE = 100
    private val GALLERY_REQUEST_CODE = 100
    var currentPhotoPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentClientSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validate()
        init()
        getDataSpinner()
        openDialogCameraOrGallery()
    }

    private fun init() {
        provincesViewModel = ViewModelProvider(
            this,
            ProvinceViewModel.ProvinceViewModelFactory(requireActivity().application)
        )[ProvinceViewModel::class.java]
        agencyViewModel = ViewModelProvider(
            this,
            AgencyViewModel.AgencyViewModelFactory(requireActivity().application)
        )[AgencyViewModel::class.java]
        clientViewModel = ViewModelProvider(
            this,
            RegisterViewModel.RegisterViewModelFactory(requireActivity().application)
        )[RegisterViewModel::class.java]
    }

    private fun getDataSpinner() {
        provincesViewModel.getProvinces().observe(viewLifecycleOwner, Observer { it ->
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        val adapterSpProvinces = ArrayAdapter(requireContext(), R.layout.list_item, it.data!!.response)
                        binding.clientSignUpSpProvinces.adapter = adapterSpProvinces
                    }
                    Status.ERROR -> {
                        Log.e("provinces", it.data!!.message.toString())
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
        binding.clientSignUpSpProvinces.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    itrProvinces = position + 1
                    Log.e("itrProvinces", "" + itrProvinces)
                    getSpinner(itrProvinces)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        getSpinner(itrProvinces)
    }

    private fun getSpinner(itrProvinces: Int) {
        binding.clientSignUpSpAgency.isEnabled = true
        agencyViewModel.getAgency(itrProvinces)
            .observe(viewLifecycleOwner, Observer { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            val adapterSpAgency = ArrayAdapter(requireContext(), R.layout.list_item, it.data!!.response)
                            binding.clientSignUpSpAgency.adapter = adapterSpAgency

                        }

                        Status.ERROR -> {

                        }

                        Status.LOADING -> {

                        }
                    }
                }
            })

        binding.clientSignUpSpAgency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent!!.getItemAtPosition(position) as Agency // Thay YourDataType bằng kiểu dữ liệu thực tế
                    val selectedId = selectedItem.id // Giả sử đối tượng có thuộc tính id
                    // Xử lý ID đã chọn
                    Log.d("Selected ID", "ID: $selectedId")
                    itrAgency = selectedId
                    Log.e("itrAgency", "" + itrAgency)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun openDialogCameraOrGallery() {
        binding.clientSignUpImgUpload.setOnClickListener {
            val dialogBinding = DialogChooseCameraOrGalleryBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = lp
            dialog.setCancelable(true)
            dialog.setContentView(dialogBinding.root)
            dialog.show()
            checkPermissions()
            dialogBinding.dialogChooseCamera.setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            null
                        }
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                requireContext(),
                                "com.freshervnc.pharmacycounter.provider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                            dialog.dismiss()
                        }
                    }
                }
            }
            dialogBinding.dialogChooseGallery.setOnClickListener {
                val mIntent = Intent()
                mIntent.action = Intent.ACTION_GET_CONTENT
                mIntent.type = "image/*"
                startActivityForResult(mIntent, GALLERY_REQUEST_CODE)
                dialog.dismiss()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            binding.clientSignUpShowImage.setImageBitmap(bitmap)
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data!!.data != null) {
                binding.clientSignUpShowImage.setImageURI(data.data)
                val selectedImageUri: Uri? = data.data
                selectedImageUri?.let {
                    try {
                        val imageFile = createImageFile()
                        val inputStream = requireContext().contentResolver.openInputStream(it)
                        val outputStream = FileOutputStream(imageFile)
                        inputStream?.copyTo(outputStream)
                        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                        binding.clientSignUpShowImage.setImageBitmap(bitmap)
                        inputStream?.close()
                        outputStream.close()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        // Handle the error
                    }
                }
            }
        }
    }

    private fun validate() {
        binding.clientSignUpBtnSignUp.setOnClickListener {
            val strFullName = binding.clientSignUpEdFullName.text.toString()
            val strEmail = binding.clientSignUpEdEmail.text.toString()
            val strPhone = binding.clientSignUpEdPhone.text.toString()
            val strPassword = binding.clientSignUpEdPassword.text.toString()
            val strAddressCounter = binding.clientSignUpEdAddressClient.text.toString()
            if (strFullName.isEmpty()) {
                binding.clientSignUpLayoutEdFullName.helperText =
                    getString(R.string.validate_tv_full_name)
            } else {
                binding.clientSignUpLayoutEdFullName.helperText = ""
            }
            if (strPhone.isEmpty()) {
                binding.clientSignUpLayoutEdPhone.helperText =
                    getString(R.string.validate_tv_name_counter)
            } else {
                binding.clientSignUpLayoutEdPhone.helperText = ""
            }
            if (strAddressCounter.isEmpty()) {
                binding.clientSignUpLayoutEdAddress.helperText =
                    getString(R.string.validate_tv_customer_address)
            } else {
                binding.clientSignUpLayoutEdAddress.helperText = ""
            }
            if (strPassword.isEmpty()) {
                binding.clientSignUpLayoutEdPassword.helperText =
                    getString(R.string.validate_tv_counter_password)
            } else {
                binding.clientSignUpLayoutEdPassword.helperText = ""
            }
            val fullNameBody: RequestBody = strFullName.toRequestBody("text/plain".toMediaType())
            val idAgency: RequestBody = itrAgency.toString().toRequestBody("text/plain".toMediaType())
            val address: RequestBody = strAddressCounter.toRequestBody("text/plain".toMediaType())
            val provinces: RequestBody = itrProvinces.toString().toRequestBody("text/plain".toMediaType())
            val phone: RequestBody = strPhone.toRequestBody("text/plain".toMediaType())
            val email: RequestBody = strEmail.toRequestBody("text/plain".toMediaType())
            val password: RequestBody = strPassword.toRequestBody("text/plain".toMediaType())
            val filePart: MultipartBody.Part
            if (currentPhotoPath != "") {
                val file = File(currentPhotoPath)
                val requestFile =
                    RequestBody.create("{multipart/form-data}".toMediaTypeOrNull(), file);
                filePart = MultipartBody.Part.createFormData("img", file.name, requestFile);
            }else{
                val file = File("")
                val requestFile =
                    RequestBody.create("{multipart/form-data}".toMediaTypeOrNull(), file);
                filePart = MultipartBody.Part.createFormData("img", file.name, requestFile);
            }
            Log.e("register",""+strFullName + "\n" + itrAgency + "\n" + strAddressCounter + "\n" + itrProvinces + "\n" + strPhone + "\n" + strEmail + "\n" + strPassword )
            clientViewModel.requestRegisterCustomer(fullNameBody, idAgency, address, provinces, phone, email, password, filePart)
                .observe(viewLifecycleOwner, Observer { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
//                                Snackbar.make(requireView(), it.data!!.response.description, 2000).show()
                                binding.clientSignUpEdFullName.setText("")
                                binding.clientSignUpEdPhone.setText("")
                                binding.clientSignUpEdEmail.setText("")
                                binding.clientSignUpEdAddressClient.setText("")
                                binding.clientSignUpEdPassword.setText("")
                                currentPhotoPath = ""
                                itrAgency = 1
                                itrProvinces = 1
                                binding.clientSignUpShowImage.setImageResource(R.drawable.baseline_hide_image_24)
                            }Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
        }
    }

    /* Kiểm tra xin quyền */
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }

    /* Tạo folder để lưu trữ ảnh */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
}