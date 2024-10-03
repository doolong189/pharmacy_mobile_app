package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.BottomDialogCreateBillBinding
import com.freshervnc.pharmacycounter.databinding.BottomDialogPaymentBinding
import com.freshervnc.pharmacycounter.databinding.FragmentPaymentConfirmBinding
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.ConfirmAdapter
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.google.android.material.bottomsheet.BottomSheetDialog


class PaymentConfirmFragment : Fragment() {
    private lateinit var binding: FragmentPaymentConfirmBinding
    private lateinit var confirmAdapter: ConfirmAdapter
    private lateinit var mySharedPrefer: SharedPrefer
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
        confirmAdapter = ConfirmAdapter()
        (activity as MainActivity).hideBottomNav()
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun getData(){
        binding.paymentRcProduct.setHasFixedSize(true)
        binding.paymentRcProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.paymentRcProduct.adapter = confirmAdapter
        confirmAdapter.setList((activity as MainActivity).getListDataConfirm())
        confirmAdapter.notifyDataSetChanged()
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
            view.dialogPaymentTvAmount.text = totalPrice.toString()
            view.dialogPaymentTvQuality.text = totalAmount.toString()
            view.dialogPaymentEdFullName.setText(mySharedPrefer.name!!.toString())
            view.dialogPaymentEdPhone.setText(mySharedPrefer.phone!!.toString())
            view.dialogPaymentEdEmail.setText(mySharedPrefer.email!!.toString())
            view.dialogPaymentEdAddress.setText(mySharedPrefer.address!!.toString())
        }
    }
    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomNav()
        (activity as MainActivity).getListData().clear()
    }

}