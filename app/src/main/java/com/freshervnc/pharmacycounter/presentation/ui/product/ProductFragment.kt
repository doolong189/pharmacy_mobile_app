package com.freshervnc.pharmacycounter.presentation.ui.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogAddToCartBinding
import com.freshervnc.pharmacycounter.databinding.FragmentProductBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse
import com.freshervnc.pharmacycounter.domain.response.search.RequestSearchResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct
import com.freshervnc.pharmacycounter.presentation.ui.cart.CartFragment
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.ChildProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.product.viewmodel.ProductViewModel
import com.freshervnc.pharmacycounter.presentation.ui.searchproduct.viewmodel.SearchViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class ProductFragment : Fragment() , OnClickItemProduct  {
    private lateinit var binding : FragmentProductBinding
    private lateinit var productViewModel : ProductViewModel
    private lateinit var childProductAdapter: ChildProductAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var cartViewModel: CartViewModel
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        getData()
    }

    private fun init(){
        productViewModel = ViewModelProvider(this,ProductViewModel.ProductViewModelFactory(requireActivity().application))[ProductViewModel::class.java]
        cartViewModel = ViewModelProvider(this, CartViewModel.CartViewModelFactory(requireActivity().application))[CartViewModel::class.java]
        searchViewModel = ViewModelProvider(this, SearchViewModel.SearchViewModelFactory(requireActivity().application))[SearchViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
        (activity as MainActivity).hideBottomNav()
    }

    private fun initVariable(){
        binding.productRcView.setHasFixedSize(true)
        binding.productRcView.layoutManager = GridLayoutManager(requireContext() , 2)
    }

    private fun getData(){
        val b = arguments
        if (b != null) {
            val strIdProduct = b.getInt("key_product")
            val strIdCategory = b.getString("key_category")
            searchProduct(strIdCategory.toString())
            var requestProductTemp = RequestProductResponse()
            if (strIdCategory == "hoat_chat"){
                requestProductTemp = RequestProductResponse(activeIngredient = strIdProduct)
            }else if(strIdCategory == "nhom_thuoc"){
                requestProductTemp = RequestProductResponse(typeMedicine = strIdProduct)
            }else if (strIdCategory == "nha_san_xuat"){
                requestProductTemp = RequestProductResponse(manufacturer = strIdProduct)
            }else if(strIdProduct == -1){
                //edittext search
                requestProductTemp = RequestProductResponse(search = strIdCategory.toString())
            }
            else {
                requestProductTemp = RequestProductResponse(category = strIdCategory.toString())
            }
            productViewModel.getProduct("Bearer "+mySharedPrefer.token, requestProductTemp ).observe(viewLifecycleOwner,
                Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                resources.data?.let { item ->
                                    binding.productSwRefresh.isRefreshing = false
                                    if (item.response.data.isEmpty()){
                                        binding.productLnEmpty.visibility = View.VISIBLE
                                    }else{
                                        binding.productLnEmpty.visibility = View.GONE
                                    }
                                    childProductAdapter = ChildProductAdapter(item.response.data,this)
                                    binding.productRcView.adapter = childProductAdapter
                                    childProductAdapter.setList(item.response.data)
                                }
                            }
                            Status.ERROR -> {
                                binding.productSwRefresh.isRefreshing = false
                            }
                            Status.LOADING -> {
                                binding.productSwRefresh.isRefreshing = true
                            }
                        }
                    }
                })
        }else{
            Log.e("log bundle o day","null")
        }
    }

    private fun searchProduct(idCategory : String){
        binding.productEdSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var requestProductTemp  = RequestProductResponse(search = s.toString() , category = idCategory)

                productViewModel.getProduct("Bearer "+mySharedPrefer.token,requestProductTemp ).observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when(resources.status){
                                Status.SUCCESS -> {
                                    resources.data?.let { item ->
                                        if (item.response.data.isEmpty()){
                                            binding.productLnSearchEmpty.visibility = View.VISIBLE
                                        }else{
                                            binding.productLnSearchEmpty.visibility = View.GONE
                                        }
                                        binding.productSwRefresh.isRefreshing = false
                                        binding.productRcView.adapter = childProductAdapter
                                        childProductAdapter.setList(item.response.data)
                                    }
                                }
                                Status.ERROR -> {
                                    binding.productSwRefresh.isRefreshing = false
                                }
                                Status.LOADING -> {
                                    binding.productSwRefresh.isRefreshing = true
                                }
                            }
                        }
                    })
            }
        })
    }

    override fun onClickItem(item: Data) {
        val view = BottomDialogAddToCartBinding.inflate(layoutInflater, null, false)
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(view.root)
        dialog.show()

        //get data
        Glide.with(requireContext()).load(item.imgUrl).error(R.drawable.ic_picture)
            .into(view.dialogBottomCartImageProduct)
        view.dialogBottomCartTvNameProduct.text = item.name
        view.dialogBottomCartTvPack.text = item.pack
        view.dialogBottomCartTvBonusCoin.text = "Tặng " + item.bonusCoins.toString()
        view.dialogBottomCartTvPrice.text = "${item.price}  VND"
        view.dialogBottomCartTvMinimum.text =
            "Số lượng: ${item.quality} \n Số lượng tối thiểu: ${item.minimumAmount} \n Số lượng tối đa: ${item.maxAmount}"
        //add to cart
        var amountTemp = 0
        view.dialogBottomCartImageMinus.setOnClickListener {
            if (amountTemp == 0) amountTemp = 0 else amountTemp -= 1
            view.dialogBottomCartTvAmount.text = amountTemp.toString()
        }
        view.dialogBottomCartImagePlus.setOnClickListener {
            amountTemp += 1
            view.dialogBottomCartTvAmount.text = amountTemp.toString()
        }
        view.dialogBottomCartBtnAddCart.setOnClickListener {
            val cartTemp: RequestCartResponse = RequestCartResponse(item.id, amountTemp)
            cartViewModel.addToCart("Bearer " + mySharedPrefer.token, cartTemp)
                .observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when (resources.status) {
                                Status.SUCCESS -> {
                                    Snackbar.make(
                                        requireView(),
                                        getString(R.string.string_add_cart_successfully),
                                        2000
                                    ).show()
                                    dialog.dismiss()
                                }

                                Status.ERROR -> {
                                    Snackbar.make(
                                        requireView(),
                                        getString(R.string.string_add_cart_failed),
                                        2000
                                    ).show()
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
        inflater.inflate(R.menu.toolbar_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.icon_toolbar_cart) {
            (activity as MainActivity).replaceFragment(CartFragment())
        }
        return super.onOptionsItemSelected(item)
    }
}