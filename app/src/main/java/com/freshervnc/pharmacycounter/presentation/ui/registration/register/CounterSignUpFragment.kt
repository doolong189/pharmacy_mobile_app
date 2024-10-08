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
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.DialogChooseCameraOrGalleryBinding
import com.freshervnc.pharmacycounter.databinding.FragmentCounterSignUpBinding
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.presentation.ui.registration.viewmodel.ProvinceViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.register.viewmodel.RegisterViewModel
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


class CounterSignUpFragment : Fragment() {
    private lateinit var binding: FragmentCounterSignUpBinding
    private lateinit var provinceViewModel: ProvinceViewModel
    private lateinit var registerViewModel: RegisterViewModel
    private var itr = 0
    private val CAMERA_REQUEST_CODE = 100
    private val GALLERY_REQUEST_CODE = 25
    private var currentPhotoPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCounterSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        validate()
        openDialogCameraOrGallery()
    }

    private fun init() {
        provinceViewModel = ViewModelProvider(
            this,
            ProvinceViewModel.ProvinceViewModelFactory(requireActivity().application)
        )[ProvinceViewModel::class.java]
        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModel.RegisterViewModelFactory(requireActivity().application)
        )[RegisterViewModel::class.java]
    }

    private fun validate() {
        binding.counterSignUpBtnSignUp.setOnClickListener {
            val strFullName = binding.counterSignUpEdFullName.text.toString()
            val strNameCounter = binding.counterSignUpEdNameCounter.text.toString()
            val strEmail = binding.counterSignUpEdEmail.text.toString()
            val strPhone = binding.counterSignUpEdPhone.text.toString()
            val strPassword = binding.counterSignUpEdPassword.text.toString()
            val strAddressCounter = binding.counterSignUpEdAddressCounter.text.toString()
            if (strFullName.isEmpty()) {
                binding.counterSignUpLayoutEdFullName.helperText =
                    getString(R.string.validate_tv_full_name)
            } else {
                binding.counterSignUpLayoutEdFullName.helperText = ""
            }

            if (strNameCounter.isEmpty()) {
                binding.counterSignUpLayoutNameCounter.helperText =
                    getString(R.string.validate_tv_name_counter)
            } else {
                binding.counterSignUpLayoutNameCounter.helperText = ""
            }

            if (strEmail.isEmpty()) {
                binding.counterSignUpLayoutEmail.helperText = ""
            } else {
                binding.counterSignUpLayoutEmail.helperText = ""

            }

            if (strAddressCounter.isEmpty()) {
                binding.counterSignUpLayoutAddressCounter.helperText =
                    getString(R.string.validate_tv_counter_address_counter)
            } else {
                binding.counterSignUpLayoutAddressCounter.helperText = ""

            }

            if (strPhone.isEmpty()) {
                binding.counterSignUpLayoutPhone.helperText =
                    getString(R.string.validate_tv_counter_phone)
            } else {
                binding.counterSignUpLayoutPhone.helperText = ""

            }

            if (strPassword.isEmpty()) {
                binding.counterSignUpLayoutPassword.helperText =
                    getString(R.string.validate_tv_counter_password)
            } else {
                binding.counterSignUpLayoutPassword.helperText = ""
            }
            if (currentPhotoPath.isEmpty()){
                binding.counterSignUpTvValidateImage.text = getString(R.string.validate_tv_counter_image)
            }else{
                binding.counterSignUpTvValidateImage.text = ""
            }

            val fullNameBody: RequestBody = strFullName.toRequestBody("text/plain".toMediaType())
            val nameCounter: RequestBody = strNameCounter.toRequestBody("text/plain".toMediaType())
            val address: RequestBody = strAddressCounter.toRequestBody("text/plain".toMediaType())
            val provinces: RequestBody = (itr + 1).toString().toRequestBody("text/plain".toMediaType())
            val phone: RequestBody = strPhone.toRequestBody("text/plain".toMediaType())
            val email: RequestBody = strEmail.toRequestBody("text/plain".toMediaType())
            val password: RequestBody = strPassword.toRequestBody("text/plain".toMediaType())

            val file = File(currentPhotoPath)
            val requestFile = file.asRequestBody("{multipart/form-data}".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("img", file.name, requestFile)
            registerViewModel.requestRegisterCounter(
                fullNameBody,
                nameCounter,
                address,
                provinces,
                phone,
                email,
                password,
                filePart
            )
                .observe(viewLifecycleOwner, Observer { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> Snackbar.make(
                                requireView(),
                                "" + it.data!!.response.decription,
                                2000
                            ).show()

                            Status.ERROR -> Snackbar.make(
                                requireView(),
                                "return code" + it.data!!.response.decription,
                                2000
                            ).show()

                            Status.LOADING -> {

                            }
                        }
                    }
                })
        }
    }

    private fun openDialogCameraOrGallery() {
        binding.counterSignUpImgUpload.setOnClickListener {
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
            binding.counterSignUpShowImage.setImageBitmap(bitmap)
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data!!.data != null) {
                binding.counterSignUpShowImage.setImageURI(data.data)
                val selectedImageUri: Uri? = data.data
                selectedImageUri?.let {
                    try {
                        val imageFile = createImageFile()
                        val inputStream = requireContext().contentResolver.openInputStream(it)
                        val outputStream = FileOutputStream(imageFile)
                        inputStream?.copyTo(outputStream)
                        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                        binding.counterSignUpShowImage.setImageBitmap(bitmap)
                        inputStream?.close()
                        outputStream.close()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getData() {
        /* spinner provinces */
        provinceViewModel.getProvinces().observe(viewLifecycleOwner, Observer { it ->
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        val adapter =
                            ArrayAdapter(requireContext(), R.layout.list_item, it.data!!.response)
                        binding.counterSignUpSpProvinces.adapter = adapter
                        binding.counterSignUpSpProvinces.setOnItemClickListener { parent, view, position, id ->
                            itr = position
                        }
                    }
                    Status.ERROR -> {
                        Log.e("provinces", it.data!!.message.toString())
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
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