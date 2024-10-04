package com.freshervnc.pharmacycounter.presentation.ui.registration.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshervnc.pharmacycounter.databinding.FragmentClientSignInBinding
import com.freshervnc.pharmacycounter.presentation.ui.registration.register.ClientSignUpFragment
import com.freshervnc.pharmacycounter.presentation.ui.registration.RegistrationActivity

class ClientSignInFragment : Fragment() {
    private lateinit var binding: FragmentClientSignInBinding
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
        goToRegister()
    }

    private fun goToRegister(){
        binding.clientSignInTvRegisterAccount.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(ClientSignUpFragment())
        }
    }
}