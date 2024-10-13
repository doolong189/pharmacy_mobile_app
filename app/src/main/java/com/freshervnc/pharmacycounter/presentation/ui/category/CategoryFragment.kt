package com.freshervnc.pharmacycounter.presentation.ui.category

import android.R.attr
import android.R.attr.value
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.databinding.FragmentCategoryBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemParentCategory
import com.freshervnc.pharmacycounter.presentation.ui.bill.fragment.DetailHistoryFragment
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.ParentCategoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class CategoryFragment : Fragment(), OnClickItemCategory , OnClickItemParentCategory{
    private lateinit var binding : FragmentCategoryBinding
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
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
    }

    private fun init() {
        categoryViewModel = ViewModelProvider(this, CategoryViewModel.CategoryViewModelFactory(requireActivity().application))[CategoryViewModel::class.java]
        parentCategoryAdapter = ParentCategoryAdapter(this,this)
        mySharedPrefer = SharedPrefer(requireContext())
        (activity as MainActivity).showBottomNav()
    }
    private fun initVariable() {
        binding.categoryRcView.setHasFixedSize(true)
        binding.categoryRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryRcView.adapter = parentCategoryAdapter
        binding.categorySwRefresh.setOnRefreshListener {
            getData()
        }
        getData()
    }
    private fun getData() {
        //1 : counter - 0 : customer
        if (mySharedPrefer.status == 1){
            categoryViewModel.getCategory("Bearer "+mySharedPrefer.token)
                .observe(viewLifecycleOwner) { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
                                binding.categorySwRefresh.isRefreshing = false
                                resources.data?.let { item ->
                                    parentCategoryAdapter.setList(item.response)
                                }
                            }
                            Status.ERROR -> {
                                binding.categorySwRefresh.isRefreshing = false
                            }
                            Status.LOADING -> {
                                binding.categorySwRefresh.isRefreshing = true
                            }
                        }
                    }
                }
        }else{
            categoryViewModel.getCustomerCategory("Bearer "+mySharedPrefer.token)
                .observe(viewLifecycleOwner) { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
                                binding.categorySwRefresh.isRefreshing = false
                                resources.data?.let { item ->
                                    parentCategoryAdapter.setList(item.response)
                                }
                            }
                            Status.ERROR -> {
                                binding.categorySwRefresh.isRefreshing = false
                            }
                            Status.LOADING -> {
                                binding.categorySwRefresh.isRefreshing = true
                            }
                        }
                    }
                }
        }
    }

    override fun onClickItem(item: Category, key: String ) {
        // go to product
        val args = Bundle()
        args.putInt("key_product", item.value)
        args.putString("key_category",key)
        val newFragment: ProductFragment = ProductFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }

    override fun onClickItem(key: String) {
        //go to category
        val args = Bundle()
        args.putString("key_category", key)
        val newFragment: CategoryTypeFragment = CategoryTypeFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }
}