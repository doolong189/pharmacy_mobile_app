package com.freshervnc.pharmacycounter.presentation.ui.manager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentClientManagerBinding
import com.freshervnc.pharmacycounter.presentation.ui.manager.profile.CustomerProfileFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.profile.viewmodel.ProfileViewModel
import com.freshervnc.pharmacycounter.presentation.ui.manager.viewmodel.ManagerViewModel
import com.freshervnc.pharmacycounter.presentation.ui.registration.RegistrationActivity
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class ClientManagerFragment : Fragment() {
    private lateinit var binding : FragmentClientManagerBinding
    private lateinit var managerViewModel: ManagerViewModel
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentClientManagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        actionButton()
    }

    private fun init(){
        (activity as MainActivity).showBottomNav()
        managerViewModel = ViewModelProvider(this,ManagerViewModel.ManagerViewModelFactory(requireActivity().application))[ManagerViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun actionButton(){
        binding.clientLnInfo.setOnClickListener {
            (activity as MainActivity).replaceFragment(CustomerProfileFragment())
        }
        binding.clientLnLogout.setOnClickListener {
            managerViewModel.requestLogOut("Bearer "+mySharedPrefer.token).observe(viewLifecycleOwner,
                Observer {
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                startActivity(
                                    Intent(requireActivity(),
                                        RegistrationActivity::class.java)
                                )
                                mySharedPrefer.clearUserData()
                                requireActivity().finish()
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
        }
    }

}