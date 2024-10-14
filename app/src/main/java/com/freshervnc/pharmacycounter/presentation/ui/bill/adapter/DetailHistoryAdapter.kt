package com.freshervnc.pharmacycounter.presentation.ui.bill.adapter

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
import com.freshervnc.pharmacycounter.domain.response.bill.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.TagAdapter
import com.freshervnc.pharmacycounter.utils.SharedPrefer

class DetailHistoryAdapter() : RecyclerView.Adapter<DetailHistoryAdapter.DetailHistoryViewHolder>() {
    private var carts: List<Data> = listOf()
    private lateinit var mySharedPrefer : SharedPrefer
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
//                Glide.with(holder.itemView.context).load(item.imgUrl)
//                    .into(binding.itemConfirmImgView)
//                binding.itemConfirmTvNameProduct.text = item.tenSanPham
                binding.itemConfirmTvPack.visibility = View.GONE
                binding.itemConfirmTvMinimum.visibility = View.GONE
                binding.itemConfirmTvMaximum.visibility = View.GONE

                Glide.with(holder.itemView.context).load(item.imgUrl).error(R.drawable.ic_picture)
                    .into(binding.itemConfirmImgView)
                binding.itemConfirmTvNameProduct.text = item.tenSanPham

                if (item.bonusCoins <= 0) {
                    binding.itemConfirmTvBonusCoin.visibility = View.GONE
                }
                binding.itemConfirmTvBonusCoin.text = "Tặng ${item.bonusCoins} coin"

                binding.itemConfirmTvPrice.text = "Giá tiền: ${item.discountPrice} VND"
                if (item.khuyenMai <= 0 || item.khuyenMai == null) {
                    binding.itemConfirmTvDiscount.visibility = View.GONE
                    binding.itemConfirmTvDiscountPrice.visibility = View.GONE
                } else {
                    binding.itemConfirmTvDiscount.text = "${item.khuyenMai}%"
                    binding.itemConfirmTvDiscountPrice.text = "Giá gốc: ${item.donGia} VND"
                    binding.itemConfirmTvDiscountPrice.paintFlags =
                        binding.itemConfirmTvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }

                if (item.soLuong <= 0 || item.soLuong == null) {
                    binding.itemConfirmTvQuality.visibility = View.GONE
                } else {
                    binding.itemConfirmTvQuality.text = "x${item.soLuong}"
                }

                mySharedPrefer = SharedPrefer(holder.itemView.context)
                if (mySharedPrefer.status != 1) {
                    val tagsAdapter = TagAdapter(item.tags)
                    binding.itemConfirmRcTags.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                    binding.itemConfirmRcTags.setAdapter(tagsAdapter)
                    binding.itemConfirmRcTags.setHasFixedSize(true)
                    tagsAdapter.notifyDataSetChanged()
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