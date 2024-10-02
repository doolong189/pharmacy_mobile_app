package com.freshervnc.pharmacycounter.presentation.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshervnc.pharmacycounter.databinding.FragmentSplashBinding
import com.freshervnc.pharmacycounter.presentation.ui.activity.RegistrationActivity
import com.freshervnc.pharmacycounter.presentation.ui.fragment.client.ClientSignUpFragment
import com.freshervnc.pharmacycounter.presentation.ui.fragment.counter.CounterSignInFragment


class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        features()
    }

    private fun features(){
        binding.splashCvCounter.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(CounterSignInFragment())
        }

        binding.splashCvClient.setOnClickListener {
            (activity as RegistrationActivity).replaceFragment(ClientSignUpFragment())
        }
    }

}