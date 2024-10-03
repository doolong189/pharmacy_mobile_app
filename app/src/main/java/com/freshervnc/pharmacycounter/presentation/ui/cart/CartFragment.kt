package com.freshervnc.pharmacycounter.presentation.ui.cart

import android.app.AlertDialog
import android.os.Bundle
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
import com.freshervnc.pharmacycounter.databinding.BottomDialogAddToCartBinding
import com.freshervnc.pharmacycounter.databinding.BottomDialogPaymentBinding
import com.freshervnc.pharmacycounter.databinding.FragmentCartBinding
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.ui.cart.adapter.CartAdapter
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.PaymentConfirmFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class CartFragment : Fragment(), OnClickItemCart {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
        features()
    }

    private fun init() {
        cartViewModel = ViewModelProvider(
            this,
            CartViewModel.CartViewModelFactory(requireActivity().application)
        )[CartViewModel::class.java]
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.HomeViewModelFactory(requireActivity().application)
        )[HomeViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
        cartAdapter = CartAdapter(this)
    }

    private fun features() {
        binding.cartBtnContinue.setOnClickListener {
            (activity as MainActivity).replaceFragment(PaymentConfirmFragment())
            (activity as MainActivity).addAllListDataConfirm((activity as MainActivity).getListData())
        }
    }

    private fun initVariable() {
        binding.cartRcProductCart.setHasFixedSize(true)
        binding.cartRcProductCart.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRcProductCart.adapter = cartAdapter
        getData()
    }

    private fun getData() {
        cartViewModel.getCart("Bearer " + mySharedPrefer.token.toString())
            .observe(viewLifecycleOwner, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let { item ->
                                cartAdapter.setList(item!!.response.products.data)
                                cartAdapter.notifyDataSetChanged()
                            }
                        }
                        Status.ERROR -> {}
                        Status.LOADING -> {}
                    }
                }
            })
    }

    override fun onClickItem(item: Data, amount: Int, status: Boolean) {
        updateSelectedItems(item, status)
        setDataToTextView()
    }

    override fun onClickItemDelete(item: Data, number: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Xóa sản phẩm")
        builder.setMessage(item.name)
        builder.setPositiveButton("Có") { dialog, which ->
            val cartTemp: RequestCartResponse = RequestCartResponse(item.id, number)
            cartViewModel.addToCart("Bearer " + mySharedPrefer.token, cartTemp)
                .observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when (resources.status) {
                                Status.SUCCESS -> {
                                    Snackbar.make(requireView(), "Delete Successfully", 2000)
                                    resources.data.let { item ->
                                        cartAdapter.setList(item!!.response.products.data)
                                        cartAdapter.notifyDataSetChanged()
                                    }
                                    (activity as MainActivity).getListData().remove(item)
                                    setDataToTextView()
                                }
                                Status.ERROR -> {}
                                Status.LOADING -> {}
                            }
                        }
                    })
        }

        builder.setNegativeButton("Không") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onStop() {
        super.onStop()
        Log.e("vong doi","onstop goi o day")
        (activity as MainActivity).showBottomNav()
        (activity as MainActivity).getListData().clear()
    }

    private fun updateSelectedItems(item: Data, isChecked: Boolean) {
        if (isChecked) {
            (activity as MainActivity).setListData(item)
        } else {
            (activity as MainActivity).getListData().remove(item)
        }
    }

    private fun setDataToTextView(){
        var totalAmount = 0
        var totalPrice = 0

        for (x in (activity as MainActivity).getListData()) {
            totalAmount += x.quality
            totalPrice += (x.price * x.quality)
        }
        binding.cartTvTotalAmount.text = "Tổng số tiền: $totalPrice"
        binding.cartTotalQuality.text = "Số lượng: $totalAmount VND"
    }
}