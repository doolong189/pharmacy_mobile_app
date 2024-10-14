package com.freshervnc.pharmacycounter.presentation.ui.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemChildProductBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct
import com.freshervnc.pharmacycounter.utils.SharedPrefer

class ChildProductAdapter(private var datas: List<Data>, private val listener: OnClickItemProduct) :
    RecyclerView.Adapter<ChildProductAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding: ItemChildProductBinding) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var mySharedPrefer: SharedPrefer
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemChildProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder) {
            val item = datas[position]
            Glide.with(holder.itemView.context).load(item.imgUrl).error(R.drawable.ic_picture)
                .into(binding.itemChildImageProduct)
            binding.itemChildTvNameProduct.text = item.name

            if (item.bonusCoins <= 0) {
                binding.itemChildTvBonusCoin.visibility = View.GONE
            }
            binding.itemChildTvBonusCoin.text = "Tặng ${item.bonusCoins} coin"

            if (item.pack == "") {
                binding.itemChildTvPack.visibility = View.GONE
            }
            binding.itemChildTvPack.text = item.pack

            if (item.minimumAmount <= 0) {
                binding.itemChildTvMinimum.visibility = View.GONE
            }
            binding.itemChildTvMinimum.text = "Số lượng tối thiểu: " + item.minimumAmount.toString()

            if (item.maxAmount <= 0) {
                binding.itemChildTvMaximum.visibility = View.GONE
            } else {
                binding.itemChildTvMaximum.text =
                    "Số lượng tối đa: " + item.minimumAmount.toString()
            }

            binding.itemChildTvPrice.text = "Giá tiền: ${item.discountPrice} VND"
            if (item.discount <= 0 || item.discount == null) {
                binding.itemChildTvDiscount.visibility = View.GONE
                binding.itemChildTvDiscountPrice.visibility = View.GONE
            } else {
                binding.itemChildTvDiscount.text = "${item.discount}%"
                binding.itemChildTvDiscountPrice.text = "Giá gốc: ${item.price} VND"
                binding.itemChildTvDiscountPrice.paintFlags =
                    binding.itemChildTvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            if (item.quality <= 0 || item.quality == null) {
                binding.itemChildTvStatus.visibility = View.VISIBLE
                binding.itemChildTvQuality.visibility = View.GONE
                binding.itemChildBtnAddCart.isEnabled = false
                binding.itemChildBtnAddCart.setBackgroundResource(R.color.gray)
            } else {
                binding.itemChildTvQuality.text = "Số lượng: ${item.quality}"
            }
            mySharedPrefer = SharedPrefer(holder.itemView.context)
            if (mySharedPrefer.status == 1) {
                val tagsAdapter = TagAdapter(item.tags)
                binding.itemChildRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemChildRcTags.setAdapter(tagsAdapter)
                binding.itemChildRcTags.setHasFixedSize(true)
                tagsAdapter.notifyDataSetChanged()
            }
            binding.itemChildBtnAddCart.setOnClickListener {
                listener.onClickItem(datas[position])
            }

        }
    }

    fun setList(list: List<Data>) {
        this.datas = list
        notifyDataSetChanged()
    }

}