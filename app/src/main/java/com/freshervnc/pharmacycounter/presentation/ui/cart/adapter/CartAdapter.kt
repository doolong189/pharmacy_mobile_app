package com.freshervnc.pharmacycounter.presentation.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart

class CartAdapter(private val listener: OnClickItemCart) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var carts: List<Data> = listOf()
    private var selectedPosition = RecyclerView.NO_POSITION
    private val checkedItems = mutableSetOf<Int>()
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
                Glide.with(holder.itemView.context).load(item.imgUrl)
                    .into(binding.itemCartImageProduct)
                binding.itemCartTvNameProduct.text = item.name
                binding.itemCartTvPackProduct.text = item.pack
                binding.itemCartTvPrice.text = "" + item.price + " VND"
                binding.itemCartTvQuality.text = ""+item.quality.toString()
                binding.itemCartTvDiscount.text = ""+item.discount + "\t \t" + item.discountPrice
                binding.itemCartCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    listener.onClickItem(item,item.quality,isChecked)
                }
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
                    listener.onClickItemDelete(item,0)
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
}