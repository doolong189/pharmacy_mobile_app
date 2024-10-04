package com.freshervnc.pharmacycounter.presentation.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentCategoryBinding
import com.freshervnc.pharmacycounter.domain.response.category.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.ParentCategoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.ParentProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.home.viewmodel.HomeViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class CategoryFragment : Fragment(), OnClickItemCategory {
    private lateinit var binding : FragmentCategoryBinding
    private lateinit var categoryViewModel : CategoryViewModel
    private lateinit var parentCategoryAdapter: ParentCategoryAdapter
    private lateinit var mySharedPrefer: SharedPrefer
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
        parentCategoryAdapter = ParentCategoryAdapter(this)
        mySharedPrefer = SharedPrefer(requireContext())
    }
    private fun initVariable() {
        binding.categoryRcView.setHasFixedSize(true)
        binding.categoryRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryRcView.adapter = parentCategoryAdapter
        getData()
    }
    private fun getData() {
        categoryViewModel.getCategory("Bearer "+mySharedPrefer.token)
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

    override fun onClickItem(item: Category) {

    }
}