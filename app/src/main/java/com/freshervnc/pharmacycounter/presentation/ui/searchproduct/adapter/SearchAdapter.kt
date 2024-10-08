package com.freshervnc.pharmacycounter.presentation.ui.searchproduct.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemSearchProductBinding
import com.freshervnc.pharmacycounter.domain.response.history.Data
import com.freshervnc.pharmacycounter.domain.response.search.Response
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemHistory
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemSearch

class SearchAdapter (private val listener : OnClickItemSearch) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var searchs: List<Response> = listOf()
    inner class SearchViewHolder(val binding: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        with(holder) {
            val item = searchs[position]
            binding.itemSearchTvProduct.text = item.tenSanPham.toString()
            if (item.banChay == 0){
                binding.itemSearchImgSellWell.visibility = View.GONE
            }else{
                binding.itemSearchImgSellWell.visibility = View.VISIBLE
            }

            if (item.khuyenMai == 0){
                binding.itemSearchImgDiscount.visibility = View.GONE
            }else{
                binding.itemSearchImgDiscount.visibility = View.VISIBLE
            }

            holder.itemView.setOnClickListener {
                listener.onClickSearch(item.tenSanPham)
            }
        }
    }

    override fun getItemCount(): Int {
        return searchs.size
    }

    fun setList(list: List<Response>) {
        this.searchs = list
        notifyDataSetChanged()
    }
}