package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.ConfirmAdapter
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.VoucherAdapter
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel.BillViewModel
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.viewmodel.PaymentConfirmViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class PaymentConfirmFragment : Fragment() {
    private lateinit var binding: FragmentPaymentConfirmBinding
    private lateinit var confirmAdapter: ConfirmAdapter
    private lateinit var voucherAdapter: VoucherAdapter
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var paymentConfirmViewModel: PaymentConfirmViewModel
    private lateinit var billViewModel: BillViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            totalPrice += (x.price * x.quality)
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

            for (x in (activity as MainActivity).getListDataConfirm()) {
                totalAmount += x.quality
                totalPrice += (x.price * x.quality)
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

            val billTemp = RequestBillResponse(listProductCartTemp,1,strName,
                strPhone,strEmail,strAddress,strFax,strNote,0,"",0,"100000")
            view.dialogPaymentBtnCreateBill.setOnClickListener {
                billViewModel.createBill("Bearer "+mySharedPrefer.token,billTemp).observe(viewLifecycleOwner,
                    Observer { it ->
                        it?.let { resources ->
                            when(resources.status){
                                Status.SUCCESS -> {
                                    Snackbar.make(requireView(),resources.data!!.response.description,2000).show()
                                    dialog.dismiss()
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
        Log.e("list temp product cart o day",""+listProductCartTemp)
        val requestTemp = RequestVoucherResponse(listProductCartTemp)
        paymentConfirmViewModel = ViewModelProvider(this,
            PaymentConfirmViewModel.PaymentConfirmViewModelFactory(requireActivity().application))[PaymentConfirmViewModel::class.java]
        paymentConfirmViewModel.getVoucher("Bearer " + mySharedPrefer.token.toString() , requestTemp)
            .observe(viewLifecycleOwner, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let { item ->
                                if (item!!.response.isEmpty()){
                                    viewVoucher.dialogVoucherLnEmpty.visibility = View.VISIBLE
                                    viewVoucher.dialogVoucherRcVoucher.visibility = View.GONE
                                }else {
                                    voucherAdapter.setList(item.response)
                                    voucherAdapter.notifyDataSetChanged()
                                    viewVoucher.dialogVoucherLnEmpty.visibility = View.GONE

                                }
                            }
                        }
                        Status.ERROR -> {
                            viewVoucher.dialogVoucherLnEmpty.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {}
                    }
                }
            })
    }
}