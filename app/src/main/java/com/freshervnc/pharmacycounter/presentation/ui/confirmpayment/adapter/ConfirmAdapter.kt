package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart

class ConfirmAdapter() : RecyclerView.Adapter<ConfirmAdapter.CartViewHolder>() {
    private var carts: List<Data> = listOf()
    inner class CartViewHolder(val binding: ItemProductConfirmPaymentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemProductConfirmPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConfirmAdapter.CartViewHolder, position: Int) {
        with(holder) {
            with(carts[position]) {
                val item = carts[position]
                Glide.with(holder.itemView.context).load(item.imgUrl)
                    .into(binding.itemConfirmTvImgView)
                binding.itemConfirmTvNameProduct.text = item.name
                binding.itemConfirmTvPack.text = item.pack
                binding.itemConfirmTvPrice.text = "" + item.price + " VND"
                binding.itemConfirmTvQuality.text = "x"+item.quality.toString()
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