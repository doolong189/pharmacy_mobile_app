package com.freshervnc.pharmacycounter.presentation.ui.manager.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentStoreBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemParentCategory
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.ParentCategoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.manager.categorytype.StoreCategoryTypeFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.product.ProductStoreFragment
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status

class StoreFragment : Fragment(), OnClickItemCategory, OnClickItemParentCategory {
    private lateinit var binding : FragmentStoreBinding
    private lateinit var categoryViewModel : CategoryViewModel
    private lateinit var parentCategoryAdapter: ParentCategoryAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private var checkCategoryType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        actionButton()
    }
    private fun init() {
        categoryViewModel = ViewModelProvider(this, CategoryViewModel.CategoryViewModelFactory(requireActivity().application))[CategoryViewModel::class.java]
        parentCategoryAdapter = ParentCategoryAdapter(this,this)
        mySharedPrefer = SharedPrefer(requireContext())
        (activity as MainActivity).hideBottomNav()
    }
    private fun initVariable() {
        binding.storeRcView.setHasFixedSize(true)
        binding.storeRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.storeRcView.adapter = parentCategoryAdapter
        getData()
    }
    private fun getData() {
        categoryViewModel.getStoreCategory("Bearer "+mySharedPrefer.token)
            .observe(viewLifecycleOwner) { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            resources.data?.let { item ->
                                parentCategoryAdapter.setList(item.response)
                            }
                        }
                        Status.ERROR -> {}
                        Status.LOADING -> {}
                    }
                }
            }
    }

    private fun actionButton(){
        binding.storeLnBanChay.setOnClickListener {
            val args = Bundle()
            args.putInt("key_product", 0)
            args.putString("key_category", "ban_chay")
            val newFragment: ProductStoreFragment = ProductStoreFragment()
            newFragment.setArguments(args)
            (activity as MainActivity).replaceFragment(newFragment)
        }
        binding.storeLnKhuyenMai.setOnClickListener {
            val args = Bundle()
            args.putInt("key_product", 0)
            args.putString("key_category", "khuyen_mai")
            val newFragment: ProductStoreFragment = ProductStoreFragment()
            newFragment.setArguments(args)
            (activity as MainActivity).replaceFragment(newFragment)
        }
        binding.storeLnAllSP.setOnClickListener {
            val args = Bundle()
            args.putInt("key_product", 0)
            args.putString("key_category", "all")
            val newFragment: ProductStoreFragment = ProductStoreFragment()
            newFragment.setArguments(args)
            (activity as MainActivity).replaceFragment(newFragment)
        }
    }

    override fun onClickItem(item: Category) {
        val args = Bundle()
        args.putInt("key_product", item.value)
        args.putString("key_category",checkCategoryType)
        val newFragment: ProductStoreFragment = ProductStoreFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }

    override fun onClickItem(key: String) {
        val args = Bundle()
        args.putString("key_category", key)
        //hoa chat - nhom thuoc - nha san xuat
        val newFragment: StoreCategoryTypeFragment = StoreCategoryTypeFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }

}