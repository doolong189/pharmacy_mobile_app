package com.freshervnc.pharmacycounter.presentation.ui.searchproduct

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentSearchProductBinding
import com.freshervnc.pharmacycounter.domain.response.search.RequestSearchResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemSearch
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.presentation.ui.searchproduct.adapter.SearchAdapter
import com.freshervnc.pharmacycounter.presentation.ui.searchproduct.viewmodel.SearchViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.snackbar.Snackbar


class SearchProductFragment : Fragment(), OnClickItemSearch {
    private lateinit var binding: FragmentSearchProductBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
    }

    private fun init() {
        (activity as MainActivity).hideBottomNav()
        searchViewModel = ViewModelProvider(
            this,
            SearchViewModel.SearchViewModelFactory(requireActivity().application)
        )[SearchViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun initVariable() {
        searchAdapter = SearchAdapter(this)
        binding.searchRcView.setHasFixedSize(true)
        binding.searchRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRcView.adapter = searchAdapter
        searchProduct()
    }


    private fun searchProduct() {
        binding.searchEdSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchViewModel.searchProduct("Bearer " + mySharedPrefer.token, RequestSearchResponse(s.toString())
                ).observe(viewLifecycleOwner,
                    Observer {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    resource.data.let { item ->
                                        searchAdapter.setList(item!!.response)
                                    }
                                }

                                Status.ERROR -> {
                                    Snackbar.make(requireView(),resource.data!!.message.toString(),2000).show()
                                }
                                Status.LOADING -> {}
                            }
                        }
                    })
            }
        })

        binding.searchImgClear.setOnClickListener {
            binding.searchEdSearch.setText("")
        }

    }

    override fun onClickSearch(str: String) {
        val args = Bundle()
        args.putInt("key_product", -1)
        args.putString("key_category", str)
        val newFragment: ProductFragment = ProductFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }
}