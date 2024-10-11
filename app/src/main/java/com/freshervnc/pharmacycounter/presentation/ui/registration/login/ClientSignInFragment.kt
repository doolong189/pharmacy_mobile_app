package com.freshervnc.pharmacycounter.presentation.ui.registration.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.databinding.FragmentClientSignInBinding
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse
import com.freshervnc.pharmacycounter.presentation.ui.registration.register.ClientSignUpFragment
import com.freshervnc.pharmacycounter.presentation.ui.registration.RegistrationActivity
import com.freshervnc.pharmacycounter.presentation.ui.registration.forgotpassword.ForgotPasswordFragment
import com.freshervnc.pharmacycounter.presentation.ui.registration.login.viewmodel.LoginViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.snackbar.Snackbar

class ClientSignInFragment : Fragment() {
    private lateinit var binding: FragmentClientSignInBinding
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
        binding = FragmentClientSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        goToRegister()
        login()
    }
    private fun init(){
        loginViewModel = ViewModelProvider(this,
            LoginViewModel.LoginViewModelFactory(requireActivity().application))[LoginViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun goToRegister(){
        binding.clientSignInTvRegisterAccount.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(ClientSignUpFragment())
        }
    }

    private fun login(){
        binding.clientSignInBtnLogin.setOnClickListener {
            val strUsername = binding.clientSignInEdPhoneCounter.text.toString()
            val strPassword = binding.clientSignInEdPasswordCounter.text.toString()
            val requestLoginTemp = RequestLoginResponse(strUsername , strPassword)
            loginViewModel.requestLoginCustomer(requestLoginTemp).observe(viewLifecycleOwner,
                Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                Snackbar.make(requireView(),it.data!!.response.description,3000).show()
                                binding.clientSignInEdPhoneCounter.setText("")
                                binding.clientSignInEdPasswordCounter.setText("")
                                mySharedPrefer.saveToken(it.data.response.token ,it.data.response.fullName , it.data.response.phone, it.data.response.email ,it.data.response.address , it.data.response.status)
                                startActivity(Intent(requireActivity(),MainActivity::class.java))
                            }
                            Status.ERROR -> {
                                it.data!!.message.let { log ->
                                    Snackbar.make(requireView(),log.toString(),3000).show()
                                }
                            }
                            Status.LOADING -> {

                            }
                        }
                    }
                })
        }

        binding.clientSignInTvForgotPassword.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(ForgotPasswordFragment())
        }
    }

}