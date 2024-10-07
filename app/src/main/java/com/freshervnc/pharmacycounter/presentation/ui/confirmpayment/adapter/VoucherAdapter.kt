package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.databinding.ItemDialogVoucherBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.response.voucher.Response
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart

class VoucherAdapter() : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {
    private var vouchers: List<Response> = listOf()


    inner class VoucherViewHolder(val binding: ItemDialogVoucherBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val binding = ItemDialogVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoucherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VoucherAdapter.VoucherViewHolder, position: Int) {
        with(holder) {
            with(vouchers[position]) {
                val item = vouchers[position]
                binding.itemVoucherTvTitle.text = item.title
                binding.itemVoucherTvContent.text = item.content
            }
        }
    }

    override fun getItemCount(): Int {
        return vouchers.size
    }

    fun setList(list: List<Response>) {
        this.vouchers = list
        notifyDataSetChanged()
    }
}