package com.freshervnc.pharmacycounter.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogAddToCartBinding
import com.freshervnc.pharmacycounter.databinding.FragmentHomeBinding
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.domain.response.homepage.Banner
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.ParentProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.SliderAdapter
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.presentation.ui.home.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() , OnClickItemProduct {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var parentProductAdapter: ParentProductAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(requireActivity().application))[HomeViewModel::class.java]
        cartViewModel = ViewModelProvider(this,CartViewModel.CartViewModelFactory(requireActivity().application))[CartViewModel::class.java]
        parentProductAdapter = ParentProductAdapter(this)
        mySharedPrefer = SharedPrefer(requireContext())
    }
    private fun initVariable() {
        binding.homeRcProduct.setHasFixedSize(true)
        binding.homeRcProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRcProduct.adapter = parentProductAdapter
        getData()
    }
    private fun slideShow(images : List<Banner>){
        val images1 = listOf(R.drawable.ic_logo, R.drawable.ic_logo, R.drawable.ic_logo)
        val adapter = SliderAdapter(images1)
        viewPager.adapter = adapter
    }
    private fun getData() {
        homeViewModel.getHome("Bearer "+mySharedPrefer.token)
            .observe(viewLifecycleOwner) { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            resources.data?.let { item ->
                                parentProductAdapter.setList(item.response.products)
                                slideShow(item.response.banners)
                            }
                        }
                        Status.ERROR -> {}
                        Status.LOADING -> {}
                    }
                }
            }
    }
    override fun onClickItem(item: Data) {
        val view = BottomDialogAddToCartBinding.inflate(layoutInflater, null, false)
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(view.root)
        dialog.show()

        //get data
        Glide.with(requireContext()).load(item.imgUrl).error(R.drawable.ic_picture).into(view.dialogBottomCartImageProduct)
        view.dialogBottomCartTvNameProduct.text = item.name
        view.dialogBottomCartTvPack.text = item.pack
        view.dialogBottomCartTvBonusCoin.text = "Tặng "+item.bonusCoins.toString()
        view.dialogBottomCartTvPrice.text = "${item.price}  VND"
        view.dialogBottomCartTvMinimum.text = "Số lượng tối thiểu: ${item.minimumAmount}  \n  ${item.quality}"
        //add to cart
        var amountTemp = 0
        view.dialogBottomCartImageMinus.setOnClickListener {
            if (amountTemp == 0) {
                amountTemp = 0
            } else {
                amountTemp -= 1
            }
            view.dialogBottomCartTvAmount.text = amountTemp.toString()
        }
        view.dialogBottomCartImagePlus.setOnClickListener {
            amountTemp += 1
            view.dialogBottomCartTvAmount.text = amountTemp.toString()
        }
        view.dialogBottomCartBtnAddCart.setOnClickListener {
            val cartTemp : RequestCartResponse = RequestCartResponse(item.id,amountTemp)
            cartViewModel.addToCart("Bearer "+mySharedPrefer.token,cartTemp).observe(viewLifecycleOwner,
                Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                Snackbar.make(requireView(),getString(R.string.string_add_cart_successfully),2000).show()
                                dialog.dismiss()
                            }
                            Status.ERROR -> {
                                Snackbar.make(requireView(),getString(R.string.string_add_cart_failed),2000).show()
                                dialog.dismiss()
                            }
                            Status.LOADING -> {

                            }
                        }
                    }
                })
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_cart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        val id = item.itemId
        if (id == R.id.icon_toolbar_cart) {
            (activity as MainActivity).replaceFragment(CartFragment())
            (activity as MainActivity).hideBottomNav()
        }
        return super.onOptionsItemSelected(item)
    }
}