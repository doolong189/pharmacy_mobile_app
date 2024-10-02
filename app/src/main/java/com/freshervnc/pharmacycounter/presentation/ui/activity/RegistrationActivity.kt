package com.freshervnc.pharmacycounter.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ActivityRegistrationBinding
import com.freshervnc.pharmacycounter.presentation.ui.fragment.splash.SplashFragment

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(SplashFragment())
    }

    fun replaceFragment(fragment : Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.registration_mainFrame,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}