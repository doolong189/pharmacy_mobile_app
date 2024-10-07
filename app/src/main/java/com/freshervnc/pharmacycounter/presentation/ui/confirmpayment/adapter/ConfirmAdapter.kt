package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.TagAdapter

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
//                Glide.with(holder.itemView.context).load(item.imgUrl)
//                    .into(binding.itemConfirmTvImgView)
//                binding.itemConfirmTvNameProduct.text = item.name
//                binding.itemConfirmTvPack.text = item.pack
//                binding.itemConfirmTvPrice.text = "" + item.price + " VND"
//                binding.itemConfirmTvQuality.text = "x"+item.quality.toString()

                Glide.with(holder.itemView.context).load(item.imgUrl).error(R.drawable.ic_picture).into(binding.itemConfirmImgView)

                binding.itemConfirmTvNameProduct.text = item.name

                if (item.bonusCoins == 0){
                    binding.itemConfirmTvBonusCoin.visibility = View.GONE
                }
                binding.itemConfirmTvBonusCoin.text = "Tặng ${item.bonusCoins} coin"

                if (item.pack == ""){
                    binding.itemConfirmTvPack.visibility = View.GONE
                }
                binding.itemConfirmTvPack.text = item.pack

                if (item.minimumAmount == 0){
                    binding.itemConfirmTvMinimum.visibility = View.GONE
                }
                binding.itemConfirmTvMinimum.text = "Số lượng tối thiểu: "+item.minimumAmount.toString()

                if (item.maxAmount == 0){
                    binding.itemConfirmTvMaximum.visibility = View.GONE
                }
                binding.itemConfirmTvMaximum.text = "Số lượng tối đa: "+item.maxAmount.toString()

                binding.itemConfirmTvPrice.text = "${item.price} VND"

                binding.itemConfirmTvQualityProduct.text = "Số lượng: ${item.quality}"

                binding.itemConfirmTvQuality.text = "x"+item.quality.toString()

                binding.itemConfirmTvPrice.text = "Giá tiền: ${item.discountPrice} VND"
                if (item.discount == 0 || item.discount == null) {
                    binding.itemConfirmTvDiscount.visibility = View.GONE
                    binding.itemConfirmTvDiscountPrice.visibility = View.GONE
                }else{
                    binding.itemConfirmTvDiscount.text = "${item.discount}%"
                    binding.itemConfirmTvDiscountPrice.text = "Giá gốc: ${item.price} VND"
                    binding.itemConfirmTvDiscountPrice.paintFlags = binding.itemConfirmTvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }

                val tagsAdapter = TagAdapter(item.tags)
                binding.itemConfirmRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemConfirmRcTags.setAdapter(tagsAdapter)
                binding.itemConfirmRcTags.setHasFixedSize(true)
                tagsAdapter.notifyDataSetChanged()
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