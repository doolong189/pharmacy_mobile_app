package com.freshervnc.pharmacycounter.presentation.ui.manager.product.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemChildProductBinding
import com.freshervnc.pharmacycounter.databinding.ItemStoreProductBinding
import com.freshervnc.pharmacycounter.domain.models.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemStoreProduct
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.ChildProductAdapter
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.TagAdapter

class StoreProductAdapter (private var datas: List<Data>, private val listener: OnClickItemStoreProduct) :
    RecyclerView.Adapter<StoreProductAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding: ItemStoreProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemStoreProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder) {
            val item = datas[position]
            Glide.with(holder.itemView.context).load(item.imgUrl).error(R.drawable.ic_picture)
                .into(binding.itemStoreImageProduct)
            binding.itemStoreTvNameProduct.text = item.name

            if (item.bonusCoins <= 0) {
                binding.itemStoreTvBonusCoin.visibility = View.GONE
            }
            binding.itemStoreTvBonusCoin.text = "Tặng ${item.bonusCoins} coin"

            if (item.pack == "") {
                binding.itemStoreTvPack.visibility = View.GONE
            }
            binding.itemStoreTvPack.text = item.pack

            if (item.minimumAmount <= 0) {
                binding.itemStoreTvMinimum.visibility = View.GONE
            }
            binding.itemStoreTvMinimum.text = "Số lượng tối thiểu: " + item.minimumAmount.toString()

            if (item.maxAmount <= 0) {
                binding.itemStoreTvMaximum.visibility = View.GONE
            } else {
                binding.itemStoreTvMaximum.text =
                    "Số lượng tối đa: " + item.minimumAmount.toString()
            }

            binding.itemStoreTvPrice.text = "Giá tiền: ${item.discountPrice} VND"
            if (item.discount <= 0 || item.discount == null) {
                binding.itemStoreTvDiscount.visibility = View.GONE
                binding.itemStoreTvDiscountPrice.visibility = View.GONE
            } else {
                binding.itemStoreTvDiscount.text = "${item.discount}%"
                binding.itemStoreTvDiscountPrice.text = "Giá gốc: ${item.price} VND"
                binding.itemStoreTvDiscountPrice.paintFlags =
                    binding.itemStoreTvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            if (item.tags != null) {
                val tagsAdapter = TagAdapter(item.tags)
                binding.itemStoreRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemStoreRcTags.setAdapter(tagsAdapter)
                binding.itemStoreRcTags.setHasFixedSize(true)
                tagsAdapter.notifyDataSetChanged()
            }else{
                val tagsAdapter = TagAdapter(listOf())
                binding.itemStoreRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemStoreRcTags.setAdapter(tagsAdapter)
                binding.itemStoreRcTags.setHasFixedSize(true)
                tagsAdapter.notifyDataSetChanged()
            }

            //action image button
            binding.itemStoreEye.setOnClickListener {

            }
            binding.itemStoreHaveInStore.setOnClickListener {

            }
            binding.itemStoreDelete.setOnClickListener {
                listener.onClickDelete(item)
            }
            binding.itemStoreEdit.setOnClickListener {
                listener.onClickUpdate(item)

            }

        }
    }

    fun setList(list: List<Data>) {
        this.datas = list
        notifyDataSetChanged()
    }
}