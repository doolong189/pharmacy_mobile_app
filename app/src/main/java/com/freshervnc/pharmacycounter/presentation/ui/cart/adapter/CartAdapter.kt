package com.freshervnc.pharmacycounter.presentation.ui.cart.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.TagAdapter

class CartAdapter(private val listener: OnClickItemCart) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var carts: List<Data> = listOf()
    private var selectedItems: MutableSet<Int> = mutableSetOf()

    inner class CartViewHolder(val binding: ItemProductCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemProductCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        with(holder) {
            with(carts[position]) {
                val item = carts[position]

                Glide.with(holder.itemView.context).load(item.imgUrl).error(R.drawable.ic_picture)
                    .into(binding.itemCartImageProduct)

                binding.itemCartTvNameProduct.text = item.name

                if (item.bonusCoins == 0) {
                    binding.itemCartTvBonusCoin.visibility = View.GONE
                }
                binding.itemCartTvBonusCoin.text = "Tặng ${item.bonusCoins} coin"

                if (item.pack == "") {
                    binding.itemCartTvPack.visibility = View.GONE
                }
                binding.itemCartTvPack.text = item.pack

                if (item.minimumAmount == 0) {
                    binding.itemCartTvMinimum.visibility = View.GONE
                }
                binding.itemCartTvMinimum.text =
                    "Số lượng tối thiểu: " + item.minimumAmount.toString()

                if (item.maxAmount == 0) {
                    binding.itemCartTvMaximum.visibility = View.GONE
                }
                binding.itemCartTvMaximum.text = "Số lượng tối đa: " + item.maxAmount.toString()

                binding.itemCartTvPrice.text = "${item.price} VND"

                binding.itemCartTvQualityProduct.text = "Số lượng: ${item.quality}"

                binding.itemCartTvQuality.text = item.quality.toString()

                binding.itemCartTvPrice.text = "Giá tiền: ${item.discountPrice} VND"
                if (item.discount == 0 || item.discount == null) {
                    binding.itemCartTvDiscount.visibility = View.GONE
                    binding.itemCartTvDiscountPrice.visibility = View.GONE
                } else {
                    binding.itemCartTvDiscount.text = "${item.discount}%"
                    binding.itemCartTvDiscountPrice.text = "Giá gốc: ${item.price} VND"
                    binding.itemCartTvDiscountPrice.paintFlags =
                        binding.itemCartTvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }

                val tagsAdapter = TagAdapter(item.tags)
                binding.itemCartRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemCartRcTags.setAdapter(tagsAdapter)
                binding.itemCartRcTags.setHasFixedSize(true)
                tagsAdapter.notifyDataSetChanged()

                binding.itemCartImageMinus.setOnClickListener {
                    if (item.quality == 0) {
                        item.quality = 0
                    } else {
                        item.quality -= 1
                    }
                    binding.itemCartTvQuality.text = item.quality.toString()
                }
                binding.itemCartImagePlus.setOnClickListener {
                    item.quality += 1
                    binding.itemCartTvQuality.text = item.quality.toString()
                }
                binding.itemCartImageDelete.setOnClickListener {
                    listener.onClickItemDelete(item, 0)
                }


                //action add cart
                holder.binding.itemCartCheckBox.isChecked = selectedItems.contains(position)
                holder.binding.itemCartCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedItems.add(position)
                    } else {
                        selectedItems.remove(position)
                    }
                    listener.onClickItem(item, item.quality, isChecked)
                    updateTotalPrice()
                }
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

    fun selectAllItems(select: Boolean) {
        if (select) {
            selectedItems.clear()
            selectedItems.addAll(carts.indices)
        } else {
            selectedItems.clear()
        }
        notifyDataSetChanged()
    }

    private fun updateTotalPrice() {
        var total = 0
        var amount = 0
        for (index in selectedItems) {
            val item = carts[index]
            total += item.discountPrice * item.quality
            amount += item.quality
        }
        listener.onUpdateTotal(total , amount)
    }
}