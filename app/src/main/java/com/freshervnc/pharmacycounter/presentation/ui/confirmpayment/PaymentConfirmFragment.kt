package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogPaymentBinding
import com.freshervnc.pharmacycounter.databinding.DialogVoucherBinding
import com.freshervnc.pharmacycounter.databinding.FragmentPaymentConfirmBinding
import com.freshervnc.pharmacycounter.domain.response.bill.RequestBillResponse
import com.freshervnc.pharmacycounter.domain.response.voucher.RequestVoucherResponse
import com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel.BillViewModel
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.ConfirmAdapter
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.VoucherAdapter
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.viewmodel.PaymentConfirmViewModel
import com.freshervnc.pharmacycounter.presentation.ui.home.HomeFragment
import com.freshervnc.pharmacycounter.presentation.ui.paymentonline.PaymentOnlineFragment
import com.freshervnc.pharmacycounter.presentation.ui.product.ProductFragment
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.notify


class PaymentConfirmFragment : Fragment() {
    private lateinit var binding: FragmentPaymentConfirmBinding
    private lateinit var confirmAdapter: ConfirmAdapter
    private lateinit var voucherAdapter: VoucherAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var paymentConfirmViewModel: PaymentConfirmViewModel
    private lateinit var billViewModel: BillViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPaymentConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        features()
    }

    private fun init() {
        (activity as MainActivity).hideBottomNav()
        confirmAdapter = ConfirmAdapter()
        voucherAdapter = VoucherAdapter()
        mySharedPrefer = SharedPrefer(requireContext())
        paymentConfirmViewModel = ViewModelProvider(this,
            PaymentConfirmViewModel.PaymentConfirmViewModelFactory(requireActivity().application))[PaymentConfirmViewModel::class.java]
        billViewModel = ViewModelProvider(this,
            BillViewModel.BillViewModelFactory(requireActivity().application))[BillViewModel::class.java]
    }

    private fun getData(){
        binding.paymentRcProduct.setHasFixedSize(true)
        binding.paymentRcProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.paymentRcProduct.adapter = confirmAdapter
        confirmAdapter.setList((activity as MainActivity).getListDataConfirm())
        confirmAdapter.notifyDataSetChanged()
        var totalAmount = 0
        var totalPrice = 0
        for (x in (activity as MainActivity).getListDataConfirm()) {
            totalAmount += x.quality
            totalPrice += (x.discountPrice * x.quality)
        }
        binding.paymentTvAmount.text = "Tổng tiền: "+totalPrice.toString() + " VND"
        binding.paymentTvQuality.text = "Số lượng: "+totalAmount.toString()
    }

    private fun features() {
        binding.paymentBtnConfirm.setOnClickListener {
            val view = BottomDialogPaymentBinding.inflate(layoutInflater, null, false)
            val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
            dialog.setContentView(view.root)
            dialog.show()
            var totalAmount = 0
            var totalPrice = 0
            var totalCoin = 0
            var totalPriceAfterUsingCoin = 0
            for (x in (activity as MainActivity).getListDataConfirm()) {
                totalAmount += x.quality
                totalPrice += (x.discountPrice * x.quality)
                totalCoin += x.bonusCoins
            }
            view.dialogPaymentTvAmount.text = "Tổng tiền: "+totalPrice.toString() + " VND"
            view.dialogPaymentTvQuality.text = "Số lượng: "+totalAmount.toString()
            view.dialogPaymentEdFullName.setText(mySharedPrefer.name!!.toString())
            view.dialogPaymentEdPhone.setText(mySharedPrefer.phone!!.toString())
            view.dialogPaymentEdEmail.setText(mySharedPrefer.email!!.toString())
            view.dialogPaymentEdAddress.setText(mySharedPrefer.address!!.toString())

            //apply voucher
            view.dialogPaymentImgChooseVoucher.setOnClickListener {
                getDataVoucher()
            }

            //create bill
            val listProductCartTemp = mutableListOf<Int>()
            for (x in (activity as MainActivity).getListDataConfirm()){
                listProductCartTemp.add(x.gioHangId)
            }
            val strName = view.dialogPaymentEdFullName.text.toString()
            val strPhone = view.dialogPaymentEdPhone.text.toString()
            val strEmail = view.dialogPaymentEdEmail.text.toString()
            val strAddress = view.dialogPaymentEdAddress.text.toString()
            val strFax = view.dialogPaymentEdFax.text.toString()
            val strNote = view.dialogPaymentEdNote.text.toString()

            //using coin
            var checkStatusCoin = 0
            view.dialogPaymentSwUsingCoin.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    totalPriceAfterUsingCoin = totalPrice - (totalCoin * 10)
                    view.dialogPaymentTvCoin.text = "Sử dụng $totalCoin coin được giảm "+(totalPriceAfterUsingCoin) + " VND"
                    view.dialogPaymentTvAmount.text = "Tổng tiền: $totalPrice VND"
                    view.dialogPaymentTvCoin.visibility = View.VISIBLE
                    checkStatusCoin = 1
                }else{
                    totalPriceAfterUsingCoin = totalPrice + (totalCoin * 10)
                    view.dialogPaymentTvCoin.visibility = View.GONE
                    view.dialogPaymentTvAmount.text = "Tổng tiền: "+totalPrice.toString() + " VND"
                    checkStatusCoin = 0
                }
            })

            //choose payment
            var checkStatusPaymentOnline = 0
            val reductionPercentage = 0.5 / 100
            view.dialogPaymentCbPaymentOnline.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    checkStatusPaymentOnline = 1
                    view.dialogPaymentTvAmount.text = "Tổng tiền: "+(totalPrice * (1 - reductionPercentage)).toInt() + " VND"
                }else{
                    checkStatusPaymentOnline = 0
                    view.dialogPaymentTvAmount.text = "Tổng tiền: "+(totalPrice * (1 + reductionPercentage)).toInt() + " VND"
                }
            })

            view.dialogPaymentBtnCreateBill.setOnClickListener {
                val billTemp = RequestBillResponse(listProductCartTemp,1,strName,
                    strPhone,strEmail,strAddress,strFax,strNote,checkStatusPaymentOnline,"",checkStatusCoin,totalPriceAfterUsingCoin.toString())
                    Log.e("bill temp o day",""+billTemp)
                billViewModel.createBill("Bearer "+mySharedPrefer.token,billTemp).observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when(resources.status){
                                Status.SUCCESS -> {
                                    Snackbar.make(requireView(), resources.data!!.response.description, 2000).show()
                                    dialog.dismiss()
//                                    (activity as MainActivity).replaceFragment(HomeFragment())
//                                    val emptyBrowserIntent = Intent()
//                                    emptyBrowserIntent.action = Intent.ACTION_VIEW
//                                    emptyBrowserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
//                                    emptyBrowserIntent.data = Uri.fromParts("http", "", null)
//                                    val targetIntent = Intent()
//                                    targetIntent.action = Intent.ACTION_VIEW
//                                    targetIntent.addCategory(Intent.CATEGORY_BROWSABLE)
//                                    targetIntent.data = Uri.parse(resources.data.response.description)
//                                    targetIntent.selector = emptyBrowserIntent
//                                   requireActivity().startActivity(targetIntent)
                                }
                                Status.ERROR -> {}
                                Status.LOADING -> {}
                            }
                        }
                    })
            }
        }
    }
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomNav()
        (activity as MainActivity).getListDataConfirm().clear()
    }

    private fun getDataVoucher(){
        val viewVoucher = DialogVoucherBinding.inflate(layoutInflater, null, false)
        val dialogVoucher = Dialog(requireContext())
        dialogVoucher.setContentView(viewVoucher.root)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogVoucher.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogVoucher.show()
        dialogVoucher.window!!.attributes = lp
        //get data
        viewVoucher.dialogVoucherRcVoucher.setHasFixedSize(true)
        viewVoucher.dialogVoucherRcVoucher.layoutManager = LinearLayoutManager(requireContext())
        viewVoucher.dialogVoucherRcVoucher.adapter = voucherAdapter
        val listProductCartTemp = mutableListOf<Int>()
        for (x in (activity as MainActivity).getListDataConfirm()){
            listProductCartTemp.add(x.gioHangId)
        }
        val requestTemp = RequestVoucherResponse(listProductCartTemp)
        paymentConfirmViewModel.getVoucher("Bearer "+mySharedPrefer.token,listProductCartTemp).observe(viewLifecycleOwner,
            Observer {
                it?.let { resources ->
                    when(resources.status){
                        Status.SUCCESS -> {
                            if (resources.data!!.response.isEmpty()){
                                viewVoucher.dialogVoucherLnEmpty.visibility = View.VISIBLE
                                viewVoucher.dialogVoucherRcVoucher.visibility = View.GONE
                            }else {
                                viewVoucher.dialogVoucherLnEmpty.visibility = View.GONE
                                viewVoucher.dialogVoucherRcVoucher.visibility = View.VISIBLE
                            }
                        }
                        Status.ERROR -> {
                            viewVoucher.dialogVoucherLnEmpty.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewVoucher.dialogVoucherBtnCancel.setOnClickListener {
            dialogVoucher.dismiss()
        }
    }
}