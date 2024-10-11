package com.freshervnc.pharmacycounter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freshervnc.pharmacycounter.databinding.ActivityMainBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.BillFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.CategoryFragment
import com.freshervnc.pharmacycounter.presentation.ui.home.HomeFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.ClientManagerFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.CounterManagerFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val listDataTemp = mutableListOf<Data>()
    val listDataConfirmTemp = mutableListOf<Data>()
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initVariable()
        replaceFragment(HomeFragment())
    }

    private fun init(){
        mySharedPrefer = SharedPrefer(this)
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
                    if (mySharedPrefer.status == 1){
                        replaceFragment(CounterManagerFragment())
                    }else{
                        replaceFragment(ClientManagerFragment())
                    }
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