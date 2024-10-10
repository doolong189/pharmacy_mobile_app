package com.freshervnc.pharmacycounter.presentation.ui.category

import android.R.attr.defaultValue
import android.R.attr.key
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
import com.freshervnc.pharmacycounter.databinding.FragmentCategoryTypeBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.ui.category.adapter.ChildCategoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel.CategoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.presentation.ui.searchproduct.SearchProductFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class CategoryTypeFragment : Fragment() , OnClickItemCategory {
    private lateinit var binding : FragmentCategoryTypeBinding
    private lateinit var categoryViewModel : CategoryViewModel
    private lateinit var childCategoryAdapter: ChildCategoryAdapter
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
        binding = FragmentCategoryTypeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        goToSearchProduct()
    }

    private fun init() {
        (activity as MainActivity).hideBottomNav()
        categoryViewModel = ViewModelProvider(this, CategoryViewModel.CategoryViewModelFactory(requireActivity().application))[CategoryViewModel::class.java]
        childCategoryAdapter = ChildCategoryAdapter(this)
        mySharedPrefer = SharedPrefer(requireContext())
    }
    private fun initVariable() {
        binding.categoryTypeRcView.setHasFixedSize(true)
        binding.categoryTypeRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryTypeRcView.adapter = childCategoryAdapter
        getData()
    }
    private fun getData() {
        val b = arguments
        if (b != null) {
            val myInt = b.getString("key_category")
            val categoryTypeTemp = RequestCategoryTypeResponse(myInt.toString())
            checkCategoryType = myInt.toString()
            categoryViewModel.getCategoryType("Bearer " +mySharedPrefer.token , categoryTypeTemp)
                .observe(viewLifecycleOwner) { it ->
                    it?.let { resources ->
                        when (resources.status) {
                            Status.SUCCESS -> {
                                resources.data?.let { item ->
                                    childCategoryAdapter.setList(item.response.data)
                                }
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                }
        }else{
            Log.e("log bundle o day","null")
        }
    }

    private fun goToSearchProduct(){
        binding.categoryTypeEdSearch.setOnClickListener {
            (activity as MainActivity).replaceFragment(SearchProductFragment())
        }
    }

    override fun onClickItem(item: Category) {
        val args = Bundle()
        args.putInt("key_product", item.value)
        args.putString("key_category",checkCategoryType)
        val newFragment: ProductFragment = ProductFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }


}