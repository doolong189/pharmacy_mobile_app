package com.freshervnc.pharmacycounter.presentation.ui.fragment.counter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.databinding.FragmentCounterSignInBinding
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse
import com.freshervnc.pharmacycounter.presentation.ui.activity.RegistrationActivity
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar


class CounterSignInFragment : Fragment() {
    private lateinit var binding : FragmentCounterSignInBinding
    private lateinit var loginViewModel : LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCounterSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        gotoRegister()
        login()
    }

    private fun init(){
        loginViewModel = ViewModelProvider(this,LoginViewModel.LoginViewModelFactory(requireActivity().application))[LoginViewModel::class.java]
    }

    private fun gotoRegister(){
        binding.counterSignInTvRegisterAccount.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(CounterSignUpFragment())
        }
    }

    private fun login(){
        binding.counterSignInBtnLogin.setOnClickListener {
            val strUsername = binding.counterSignInEdPhoneCounter.text.toString()
            val strPassword = binding.counterSignInEdPasswordCounter.text.toString()
            val requestLoginTemp = RequestLoginResponse(strUsername , strPassword)
            loginViewModel.requestLoginCounter(requestLoginTemp).observe(viewLifecycleOwner,
                Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                Snackbar.make(requireView(),"Login Successfully",2000).show()
                                binding.counterSignInEdPhoneCounter.setText("")
                                binding.counterSignInEdPasswordCounter.setText("")
                                startActivity(Intent(requireActivity(),MainActivity::class.java))

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
}