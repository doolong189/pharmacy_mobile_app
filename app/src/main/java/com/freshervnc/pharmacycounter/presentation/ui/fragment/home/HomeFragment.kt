package com.freshervnc.pharmacycounter.presentation.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentHomeBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Banner
import com.freshervnc.pharmacycounter.utils.Contacts
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var parentProductAdapter: ParentProductAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
    }

    private fun init() {
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.HomeViewModelFactory(requireActivity().application)
        )[HomeViewModel::class.java]
        parentProductAdapter = ParentProductAdapter()
    }

    private fun initVariable() {
        binding.homeRcProduct.setHasFixedSize(true)
        binding.homeRcProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRcProduct.adapter = parentProductAdapter
        getData()
    }

    private fun slideShow(images : List<Banner>){
        val adapter = SliderAdapter(images)
        viewPager.adapter = adapter
    }

    private fun getData() {
        homeViewModel.getHome("Bearer "+Contacts.authen)
            .observe(viewLifecycleOwner) { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            resources.data?.let { product ->
                                parentProductAdapter.setList(product.response.products)
                            }
                            resources.data?.let { banners ->
                                slideShow(banners.response.banners)
                            }
                        }

                        Status.ERROR -> {

                        }

                        Status.LOADING -> {

                        }
                    }
                }
            }
    }
}