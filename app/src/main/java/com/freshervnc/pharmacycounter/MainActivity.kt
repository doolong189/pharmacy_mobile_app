package com.freshervnc.pharmacycounter

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.freshervnc.pharmacycounter.databinding.ActivityMainBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.home.HomeFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mySharedPrefer: SharedPrefer
    val listDataTemp = mutableListOf<Data>()
    val listDataConfirmTemp = mutableListOf<Data>()
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

    fun showBottomNav(){
        binding.mainBottomNav.visibility = View.VISIBLE
    }

    fun hideBottomNav(){
        binding.mainBottomNav.visibility = View.GONE
    }

    fun getListData() : MutableList<Data>{
        return listDataTemp
    }

    fun setListData(item : Data){
        listDataTemp.add(item)
    }

    fun addAllListData(item : List<Data>){
        listDataTemp.addAll(item)
    }

    fun removeData(item : Data){
        listDataTemp.remove(item)
    }

    fun getListDataConfirm() : MutableList<Data>{
        return listDataConfirmTemp
    }

    fun addAllListDataConfirm(item : List<Data>){
        listDataConfirmTemp.addAll(item)
    }
}