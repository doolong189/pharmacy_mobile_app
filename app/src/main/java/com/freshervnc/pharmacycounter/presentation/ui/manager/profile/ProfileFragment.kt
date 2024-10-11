package com.freshervnc.pharmacycounter.presentation.ui.manager.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentProfileBinding
import com.freshervnc.pharmacycounter.presentation.ui.manager.profile.viewmodel.ProfileViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.viewmodel.ProvinceViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var provincesViewModel: ProvinceViewModel
    private var itGenCy = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
    }

    private fun init() {
        (activity as MainActivity).hideBottomNav()
        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModel.ProfileViewModelFactory(requireActivity().application)
        )[ProfileViewModel::class.java]
        provincesViewModel = ViewModelProvider(
            this,
            ProvinceViewModel.ProvinceViewModelFactory(requireActivity().application)
        )[ProvinceViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun getData() {
        profileViewModel.getProfile("Bearer " + mySharedPrefer.token).observe(viewLifecycleOwner,
            Observer { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            resources.data.let { item ->
                                Log.e("profile", "" + item!!.response)
                                Glide.with(requireContext()).load(item!!.response.img)
                                    .into(binding.profileImgUser)
                                if (item.response.ten == null) {
                                    binding.profileEdName.setText("")
                                } else {
                                    binding.profileEdName.setText("${item.response.ten}")
                                }
                                if (item.response.tenNhaThuoc == null) {
                                    binding.profileEdNameCounter.setText("")
                                } else {
                                    binding.profileEdNameCounter.setText("${item.response.tenNhaThuoc}")
                                }
                                if (item.response.diaChi == null) {
                                    binding.profileEdAddress.setText("")
                                } else {
                                    binding.profileEdAddress.setText("${item.response.diaChi}")
                                }

                                if (item.response.tinh == null) {
                                    itGenCy = 0
                                } else {
                                    itGenCy += item.response.tinh
                                }

                                if (item.response.maSoThue == null) {
                                    binding.profileEdFax.setText("")
                                } else {
                                    binding.profileEdFax.setText("${item.response.maSoThue}")

                                }
                                if (item.response.email == null) {
                                    binding.profileEdEmail.setText("")
                                } else {
                                    binding.profileEdEmail.setText("${item.response.email}")
                                }

                                provincesViewModel.getProvinces().observe(viewLifecycleOwner, Observer { it ->
                                    it?.let { resources ->
                                        when (resources.status) {
                                            Status.SUCCESS -> {
                                                val adapter = ArrayAdapter(requireContext(),  R.layout.list_item, it.data!!.response)
                                                binding.profileSpGenCy.setAdapter(adapter)
                                                binding.profileSpGenCy.setOnItemClickListener { parent, view, position, id ->
                                                    val selectedItem = item.response.tinh.toString()
                                                    binding.profileSpGenCy.setText(selectedItem, false)
                                                }
                                            }

                                            //
                                            Status.ERROR -> {
                                                Log.e("provinces", it.data!!.message.toString())
                                            }

                                            //
                                            Status.LOADING -> {

                                            }
                                        }
                                    }
                                })
                            }
                        }

                        Status.ERROR -> {

                        }

                        Status.LOADING -> {

                        }
                    }
                }
            })
    }

}