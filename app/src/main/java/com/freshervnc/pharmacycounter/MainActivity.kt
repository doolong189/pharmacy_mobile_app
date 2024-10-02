package com.freshervnc.pharmacycounter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.freshervnc.pharmacycounter.databinding.ActivityMainBinding
import com.freshervnc.pharmacycounter.presentation.ui.fragment.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.fragment.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVariable()
        replaceFragment(HomeFragment())
    }

    private fun initVariable() {
        setSupportActionBar(binding.mainToolbar)
        binding.mainBottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.icon_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.icon_cart -> {
                    replaceFragment(CartFragment())
                    true
                }

                R.id.icon_history -> {
                    replaceFragment(CartFragment())
                    true
                }

                else -> {false}
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}