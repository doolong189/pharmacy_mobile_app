package com.freshervnc.pharmacycounter.presentation.ui.registration.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentCounterSignInBinding
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse
import com.freshervnc.pharmacycounter.presentation.ui.registration.register.CounterSignUpFragment
import com.freshervnc.pharmacycounter.presentation.ui.registration.RegistrationActivity
import com.freshervnc.pharmacycounter.presentation.ui.registration.forgotpassword.ForgotPasswordFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.presentation.ui.registration.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar


class CounterSignInFragment : Fragment() {
    private lateinit var binding : FragmentCounterSignInBinding
    private lateinit var loginViewModel : LoginViewModel
    private lateinit var mySharedPrefer: SharedPrefer
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
        loginViewModel = ViewModelProvider(this,
            LoginViewModel.LoginViewModelFactory(requireActivity().application))[LoginViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
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
                                binding.counterSignInPgLoading.visibility = View.GONE
                                Snackbar.make(requireView(),it.data!!.response.description,3000).show()
                                binding.counterSignInEdPhoneCounter.setText("")
                                binding.counterSignInEdPasswordCounter.setText("")
                                mySharedPrefer.saveToken(it.data.response.token ,it.data.response.fullName , it.data.response.phone, it.data.response.email ,it.data.response.address, it.data.response.status)
                                startActivity(Intent(requireActivity(),MainActivity::class.java))
                            }
                            Status.ERROR -> {
                                binding.counterSignInPgLoading.visibility = View.GONE
                                it.data!!.message.let {item ->
                                    Snackbar.make(requireView(),
                                        getString(R.string.validate_error_login),3000).show()
                                }
                            }
                            Status.LOADING -> {
                                binding.counterSignInPgLoading.visibility = View.VISIBLE
                            }
                        }
                    }
                })
        }
        binding.counterSignInTvForgotPassword.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(ForgotPasswordFragment())
        }
    }
}