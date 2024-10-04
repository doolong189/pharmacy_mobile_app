package com.freshervnc.pharmacycounter.presentation.ui.bill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.response.bill.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart

class DetailHistoryAdapter() : RecyclerView.Adapter<DetailHistoryAdapter.DetailHistoryViewHolder>() {
    private var carts: List<Data> = listOf()
    private var selectedPosition = RecyclerView.NO_POSITION
    private val checkedItems = mutableSetOf<Int>()
    inner class DetailHistoryViewHolder(val binding: ItemProductConfirmPaymentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHistoryViewHolder {
        val binding =
            ItemProductConfirmPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailHistoryAdapter.DetailHistoryViewHolder, position: Int) {
        with(holder) {
            with(carts[position]) {
                val item = carts[position]
                Glide.with(holder.itemView.context).load(item.imgUrl)
                    .into(binding.itemConfirmTvImgView)
                binding.itemConfirmTvNameProduct.text = item.tenSanPham
                binding.itemConfirmTvPack.visibility = View.GONE
                binding.itemConfirmTvPrice.text = "" + item.donGia + " VND"
                binding.itemConfirmTvQuality.text = "x"+item.soLuong.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    fun setList(list: List<Data>) {
        this.carts = list
        notifyDataSetChanged()
    }
}