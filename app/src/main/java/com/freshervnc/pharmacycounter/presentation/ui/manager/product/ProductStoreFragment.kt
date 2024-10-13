package com.freshervnc.pharmacycounter.presentation.ui.manager.product

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogPaymentBinding
import com.freshervnc.pharmacycounter.databinding.DialogCategoryBinding
import com.freshervnc.pharmacycounter.databinding.DialogProductBinding
import com.freshervnc.pharmacycounter.databinding.DialogVoucherBinding
import com.freshervnc.pharmacycounter.databinding.FragmentProductStoreBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.domain.response.product.RequestDeleteProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestShowProduct
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestCreateCategoryResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemStoreProduct
import com.freshervnc.pharmacycounter.presentation.ui.category.CategoryTypeFragment
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.ChildProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.manager.categorytype.StoreCategoryTypeFragment
import com.freshervnc.pharmacycounter.presentation.ui.manager.product.adapter.StoreProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.product.viewmodel.ProductViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class ProductStoreFragment : Fragment(), OnClickItemStoreProduct {
    private lateinit var binding : FragmentProductStoreBinding
    private lateinit var productViewModel : ProductViewModel
    private lateinit var storeProductAdapter: StoreProductAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentProductStoreBinding.inflate(layoutInflater, container, false)
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
        mySharedPrefer = SharedPrefer(requireContext())
        (activity as MainActivity).hideBottomNav()
    }

    private fun initVariable(){
        binding.storeRcView.setHasFixedSize(true)
        binding.storeRcView.layoutManager = GridLayoutManager(requireContext() , 2)
    }

    private fun getData() {
        val b = arguments
        if (b != null) {
            val strIdProduct = b.getInt("key_product")
            val strIdCategory = b.getString("key_category")
            var requestProductTemp = RequestProductResponse()
            if (strIdCategory == "hoat_chat") {
                requestProductTemp = RequestProductResponse(activeIngredient = strIdProduct)
            } else if (strIdCategory == "nhom_thuoc") {
                requestProductTemp = RequestProductResponse(typeMedicine = strIdProduct)
            } else if (strIdCategory == "nha_san_xuat") {
                requestProductTemp = RequestProductResponse(manufacturer = strIdProduct)

            } else if (strIdProduct == -1) {
                requestProductTemp = RequestProductResponse(search = strIdCategory.toString())
            } else {
                requestProductTemp = RequestProductResponse(category = strIdCategory.toString())
            }
            productViewModel.getStoreProduct("Bearer " + mySharedPrefer.token, requestProductTemp)
                .observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when (resources.status) {
                                Status.SUCCESS -> {
                                    resources.data?.let { item ->
                                        storeProductAdapter =
                                            StoreProductAdapter(item.response.data, this)
                                        binding.storeRcView.adapter = storeProductAdapter
                                        storeProductAdapter.setList(item.response.data)
                                    }
                                }

                                Status.ERROR -> {}
                                Status.LOADING -> {}
                            }
                        }
                    })
        } else {
            Log.e("log bundle o day", "null")
        }
    }

    override fun onClickDelete(item: Data) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.str_xoa_san_pham) + " " + item.name)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            productViewModel.deleteProduct("Bearer "+mySharedPrefer.token,
                RequestDeleteProductResponse(item.id)
            ).observe(viewLifecycleOwner,
                Observer {
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                getData()
                                storeProductAdapter.notifyDataSetChanged()
                                dialog.dismiss()
                            }
                            Status.ERROR -> {
                                Snackbar.make(requireView(),resources.data!!.message.toString(),3000).show()
                                dialog.dismiss()
                            }
                            Status.LOADING -> {}
                        }
                    }
                })
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onClickUpdate(item: Data) {

    }

    override fun onClickHaveInStore(id : Int) {
        val showProductTemp = RequestShowProduct(id)
        productViewModel.getShowProduct("Bearer "+mySharedPrefer.token ,showProductTemp ).observe(viewLifecycleOwner, Observer {
            it?.let{ resources ->
                when(resources.status){
                    Status.SUCCESS -> {
                        Snackbar.make(requireView(),resources.data!!.response.description,3000).show()
                    }
                    Status.ERROR -> {
                        resources.data!!.message.let {item ->
                            Snackbar.make(requireView(),item.toString(),3000).show()
                        }
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    override fun onClickBestSeller(id: Int) {
        val bestSellerProductTemp = RequestShowProduct(id)
        productViewModel.getBestSellerProduct("Bearer "+mySharedPrefer.token ,bestSellerProductTemp ).observe(viewLifecycleOwner, Observer {
            it?.let{ resources ->
                when(resources.status){
                    Status.SUCCESS -> {
                        Snackbar.make(requireView(),resources.data!!.response.description,3000).show()
                    }
                    Status.ERROR -> {
                        resources.data!!.message.let {item ->
                            Snackbar.make(requireView(),item.toString(),3000).show()
                        }
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_store_category_type, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.icon_toolbar_store_category) {
            binding.storeWebView.visibility = View.VISIBLE
            val urlString = "http://18.138.176.213/agency/products/create"
            binding.storeWebView.webViewClient = WebViewClient()
            val bearer = "Bearer " + mySharedPrefer.token
            val headerMap = HashMap<String,String>()
            headerMap["Authorization"] = bearer
            binding.storeWebView.loadUrl(urlString, headerMap)
            binding.storeWebView.settings.javaScriptEnabled = true
            binding.storeWebView.settings.setSupportZoom(true)
        }
        return super.onOptionsItemSelected(item)
    }
}