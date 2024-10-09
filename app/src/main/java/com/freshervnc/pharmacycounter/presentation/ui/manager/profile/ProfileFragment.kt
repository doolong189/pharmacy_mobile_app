package com.freshervnc.pharmacycounter.presentation.ui.manager.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentProfileBinding
import com.freshervnc.pharmacycounter.presentation.ui.manager.profile.viewmodel.ProfileViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mySharedPrefer: SharedPrefer
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
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun getData() {
        profileViewModel.getProfile("Bearer " + mySharedPrefer.token).observe(viewLifecycleOwner,
            Observer { it ->
               it?.let { resources ->
                   when(resources.status){
                       Status.SUCCESS ->{
                           resources.data.let { item ->
                               Glide.with(requireContext()).load(item!!.response.img).into(binding.profileImgUser)
                               binding.profileEdName.setText("${item.response.ten}")
                               binding.profileEdNameCounter.setText("${item.response.tenNhaThuoc}")
                               binding.profileEdAddress.setText("${item.response.diaChi}")
                               binding.profileEdGency.setText("${item.response.tinh}")
                               binding.profileEdFax.setText("${item.response.maSoThue}")
                               binding.profileEdEmail.setText("${item.response.email}")
                           }
                       }
                       Status.ERROR -> {}
                       Status.LOADING ->{}
                   }
               }
            })
    }
}