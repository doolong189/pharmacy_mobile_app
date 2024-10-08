package com.freshervnc.pharmacycounter

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freshervnc.pharmacycounter.databinding.ActivityMainBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.BillFragment
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.CategoryFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.home.HomeFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.ManagerFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

                R.id.icon_category -> {
                    replaceFragment(CategoryFragment())
                    true
                }

                R.id.icon_history -> {
                    replaceFragment(BillFragment())
                    true
                }

                R.id.icon_manager_store -> {
                    replaceFragment(ManagerFragment())
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
    fun removeAllListData(item : List<Data>){
        listDataTemp.clear()
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