package com.freshervnc.pharmacycounter.presentation.ui.manager.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentCustomerContactBinding
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemContact
import com.freshervnc.pharmacycounter.presentation.ui.manager.ClientManagerFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.CounterManagerFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.adapter.ContactAdapter
import com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.viewmodel.ContactViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class CustomerContactFragment : Fragment(), OnClickItemContact {
    private lateinit var binding : FragmentCustomerContactBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var adapter : ContactAdapter
    private val REQUEST_CALL_PHONE = 1
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        getData()
        actionButton()
    }

    private fun init(){
        (activity as MainActivity).hideBottomNav()
        contactViewModel = ViewModelProvider(this,
            ContactViewModel.ContactViewModelFactory(requireActivity().application))[ContactViewModel::class.java]
        adapter = ContactAdapter(this)
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun initVariable() {
        binding.contactRcView.setHasFixedSize(true)
        binding.contactRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.contactRcView.adapter = adapter
        binding.contactSwRefresh.setOnRefreshListener {
            getData()
        }
        getData()
    }

    private fun getData() {
        contactViewModel.getCustomerContact("Bearer "+mySharedPrefer.token)
            .observe(viewLifecycleOwner) { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            binding.contactSwRefresh.isRefreshing = false
                            resources.data?.let { item ->
                                adapter.setList(item.response.contact)
                                binding.contactTvName.text = "Tên nhà thuốc: "+item.response.info.tenNhaThuoc
                                binding.contactTvEmail.text = item.response.info.email
                                binding.contactTvAddress.text = "Địa chỉ: "+item.response.info.diaChi
                                binding.contactTvProvinces.text = item.response.info.tinh
                            }
                        }
                        Status.ERROR -> {
                            binding.contactSwRefresh.isRefreshing = false
                        }
                        Status.LOADING -> {
                            binding.contactSwRefresh.isRefreshing = true
                        }
                    }
                }
            }
    }

    override fun onClickItem(url: String , type : Int) {
        if (type == 1) {
            openLink(url)
        }else{
            makePhoneCall(url)
        }
    }


    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PHONE)
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(phoneNumber)
            startActivity(callIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val phoneNumber = "xxxxxxxxxx"
                makePhoneCall(phoneNumber)
            } else {
                Toast.makeText(requireContext(), "Permission denied to make calls", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openLink(url : String){
        val emptyBrowserIntent = Intent()
        emptyBrowserIntent.action = Intent.ACTION_VIEW
        emptyBrowserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
        emptyBrowserIntent.data = Uri.fromParts("http", "", null)
        val targetIntent = Intent()
        targetIntent.action = Intent.ACTION_VIEW
        targetIntent.addCategory(Intent.CATEGORY_BROWSABLE)
        targetIntent.data = Uri.parse(url)
        targetIntent.selector = emptyBrowserIntent
        requireActivity().startActivity(targetIntent)
    }


    private fun actionButton(){
        binding.contactBtnClose.setOnClickListener {
            (activity as MainActivity).replaceFragment(ClientManagerFragment())
        }
    }

}