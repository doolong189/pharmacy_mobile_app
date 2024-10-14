package com.freshervnc.pharmacycounter.presentation.ui.bill.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.DialogInfoDetailHistoryBinding
import com.freshervnc.pharmacycounter.databinding.DialogVoucherBinding
import com.freshervnc.pharmacycounter.databinding.FragmentDetailHistoryBinding
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse
import com.freshervnc.pharmacycounter.presentation.ui.bill.adapter.DetailHistoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.bill.adapter.HistoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel.HistoryViewModel
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter.ConfirmAdapter
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialog


class DetailHistoryFragment : Fragment() {
    private lateinit var binding: FragmentDetailHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var mySharedPrefer: SharedPrefer
    private lateinit var detailHistoryAdapter: DetailHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()

    }

    private fun init(){
        (activity as MainActivity).hideBottomNav()
        mySharedPrefer = SharedPrefer(requireContext())
        detailHistoryAdapter = DetailHistoryAdapter()
        historyViewModel = ViewModelProvider(this,HistoryViewModel.HistoryViewModelFactory(requireActivity().application))[HistoryViewModel::class.java]
    }

    private fun initVariable(){
        binding.detailHistoryRcHistory.setHasFixedSize(true)
        binding.detailHistoryRcHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.detailHistoryRcHistory.adapter = detailHistoryAdapter
        getDataBundle()
    }

    private fun getDataBundle(){
        /* get bundle from  purchase or sales*/
        val b = arguments
        val id = b!!.getInt("id")
        val page = b.getInt("page")
        val check = b.getInt("check")
        val requestDetailBillTemp = RequestDetailBillResponse(id,page)
        if (mySharedPrefer.status == 1){ // counter - customer
            //check buy history - sell history
            //1 : buy - !1 : sell
            if (check == 1){
                historyViewModel.detailBill("Bearer "+mySharedPrefer.token ,requestDetailBillTemp).observe(viewLifecycleOwner, Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                detailHistoryAdapter.setList(resources.data!!.response.products.data)
                                detailHistoryAdapter.notifyDataSetChanged()
                                binding.detailHistoryTvQuality.text = "Số lượng: "+resources.data.response.totalProduct.toString()
                                binding.detailHistoryTvTotalAmount.text = "Tổng tiền: "+resources.data.response.totalPrice.toString() + " VND"

                                //
                                binding.detailHistoryBtnInfoDetail.setOnClickListener {
                                    val viewBinding = DialogInfoDetailHistoryBinding.inflate(layoutInflater, null, false)
                                    val dialogBinding = BottomSheetDialog(requireContext())
                                    dialogBinding.setContentView(viewBinding.root)
                                    dialogBinding.show()
                                    viewBinding.dialogInfoDetailTvIdBill.text = "Đơn hàng số " +id.toString()
                                    viewBinding.dialogInfoDetailTvDate.text = resources.data.response.dateTime
                                    viewBinding.dialogInfoDetailTvTotalAmount.text = resources.data.response.totalPrice.toString() + " VND"
                                    viewBinding.dialogInfoDetailTvTotal.text = resources.data.response.price.toString() + " VND"
                                    if (resources.data.response.coinBonus == 0){
                                        viewBinding.dialogInfoDetailLnUsingCoin.visibility = View.GONE
                                        viewBinding.dialogInfoDetailLnCoin.visibility = View.GONE
                                    }else {
                                        viewBinding.dialogInfoDetailTvCoin.text =
                                            "+" + resources.data.response.coinBonus.toString() + " VND"
                                        viewBinding.dialogInfoDetailTvUsingCoin.text =
                                            "-" + (resources.data.response.coinBonus * 10).toString() + " coin"
                                    }
                                }
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
            }else{
                historyViewModel.detailAgencyHistory("Bearer "+mySharedPrefer.token ,requestDetailBillTemp).observe(viewLifecycleOwner, Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                detailHistoryAdapter.setList(resources.data!!.response.products.data)
                                detailHistoryAdapter.notifyDataSetChanged()
                                binding.detailHistoryTvQuality.text = "Số lượng: "+resources.data.response.totalProduct.toString()
                                binding.detailHistoryTvTotalAmount.text = "Tổng tiền: "+resources.data.response.totalPrice.toString() + " VND"

                                //
                                binding.detailHistoryBtnInfoDetail.setOnClickListener {
                                    val viewBinding = DialogInfoDetailHistoryBinding.inflate(layoutInflater, null, false)
                                    val dialogBinding = BottomSheetDialog(requireContext())
                                    dialogBinding.setContentView(viewBinding.root)
                                    dialogBinding.show()
                                    viewBinding.dialogInfoDetailTvIdBill.text = "Đơn hàng số " +id.toString()
                                    viewBinding.dialogInfoDetailTvDate.text = resources.data.response.dateTime
                                    viewBinding.dialogInfoDetailTvTotalAmount.text = resources.data.response.totalPrice.toString() + " VND"
                                    viewBinding.dialogInfoDetailTvTotal.text = resources.data.response.price.toString() + " VND"
                                    if (resources.data.response.coinBonus == 0){
                                        viewBinding.dialogInfoDetailLnUsingCoin.visibility = View.GONE
                                        viewBinding.dialogInfoDetailLnCoin.visibility = View.GONE
                                    }else {
                                        viewBinding.dialogInfoDetailTvCoin.text =
                                            "+" + resources.data.response.coinBonus.toString() + " VND"
                                        viewBinding.dialogInfoDetailTvUsingCoin.text =
                                            "-" + (resources.data.response.coinBonus * 10).toString() + " coin"
                                    }
                                }
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
            }

        }else{
            if (check == 1){
                historyViewModel.detailCustomerBill("Bearer "+mySharedPrefer.token ,requestDetailBillTemp).observe(viewLifecycleOwner, Observer { it ->
                    it?.let { resources ->
                        when(resources.status){
                            Status.SUCCESS -> {
                                detailHistoryAdapter.setList(resources.data!!.response.products.data)
                                detailHistoryAdapter.notifyDataSetChanged()
                                binding.detailHistoryTvQuality.text = "Số lượng: "+resources.data.response.totalProduct.toString()
                                binding.detailHistoryTvTotalAmount.text = "Tổng tiền: "+resources.data.response.totalPrice.toString() + " VND"

                                //
                                binding.detailHistoryBtnInfoDetail.setOnClickListener {
                                    val viewBinding = DialogInfoDetailHistoryBinding.inflate(layoutInflater, null, false)
                                    val dialogBinding = BottomSheetDialog(requireContext())
                                    dialogBinding.setContentView(viewBinding.root)
                                    dialogBinding.show()
                                    viewBinding.dialogInfoDetailTvIdBill.text = "Đơn hàng số " +id.toString()
                                    viewBinding.dialogInfoDetailTvDate.text = resources.data.response.dateTime
                                    viewBinding.dialogInfoDetailTvTotalAmount.text = resources.data.response.totalPrice.toString() + " VND"
                                    viewBinding.dialogInfoDetailTvTotal.text = resources.data.response.price.toString() + " VND"
                                    if (resources.data.response.coinBonus == 0){
                                        viewBinding.dialogInfoDetailLnUsingCoin.visibility = View.GONE
                                        viewBinding.dialogInfoDetailLnCoin.visibility = View.GONE
                                    }else {
                                        viewBinding.dialogInfoDetailTvCoin.text =
                                            "+" + resources.data.response.coinBonus.toString() + " VND"
                                        viewBinding.dialogInfoDetailTvUsingCoin.text =
                                            "-" + (resources.data.response.coinBonus * 10).toString() + " coin"
                                    }
                                }
                            }
                            Status.ERROR -> {}
                            Status.LOADING -> {}
                        }
                    }
                })
            }else{

            }
        }

    }

}