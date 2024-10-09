package com.freshervnc.pharmacycounter.presentation.ui.cart

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentCartBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.ui.cart.adapter.CartAdapter
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.PaymentConfirmFragment
import com.freshervnc.pharmacycounter.presentation.ui.home.viewmodel.HomeViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.snackbar.Snackbar


class CartFragment : Fragment(), OnClickItemCart {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private val selectedProductIds = mutableSetOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        (activity as MainActivity).hideBottomNav()
        (requireActivity() as MainActivity).supportActionBar!!.hide()
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
            if ((activity as MainActivity).getListData().isEmpty()) {
                Snackbar.make(requireView(), "Yêu cầu chọn sản phẩm", 3000).show()
            } else {
                (activity as MainActivity).replaceFragment(PaymentConfirmFragment())
                (activity as MainActivity).addAllListDataConfirm((activity as MainActivity).getListData())
                binding.iconCartCheckBox1.isChecked = false
            }
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
                                binding.iconCartCheckBox1.setOnCheckedChangeListener { _, isChecked ->
                                    cartAdapter.selectAllItems(isChecked)
                                }
                            }
                        }

                        Status.ERROR -> {}
                        Status.LOADING -> {}
                    }
                }
            })
    }

    override fun onClickItem(item: Data, amount: Int, status: Boolean) {
        if (!status) {
            binding.iconCartCheckBox1.isChecked = false
        }
        updateSelectedItems(item, status)
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

    override fun onUpdateTotal(total: Int, amount: Int) {
        binding.cartTvTotalAmount.text = "$total VND"
        binding.cartTotalQuality.text = "$amount"
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).getListData().clear()
        (requireActivity() as MainActivity).supportActionBar!!.show()
    }

    private fun updateSelectedItems(item: Data, isChecked: Boolean) {
        if (isChecked) {
            (activity as MainActivity).setListData(item)
        } else {
            (activity as MainActivity).getListData().remove(item)
        }
    }

    private fun setDataToTextView() {
        var totalQuality = 0
        var totalAmount = 0
        for (x in (activity as MainActivity).getListData()) {
            totalQuality += x.quality
            totalAmount += (x.discountPrice * x.quality)
        }
        binding.cartTotalQuality.text = "$totalQuality"
        binding.cartTvTotalAmount.text = "$totalAmount VND"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_cart, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.icon_cart_checkBox) {
        }
        return super.onOptionsItemSelected(item)
    }
}