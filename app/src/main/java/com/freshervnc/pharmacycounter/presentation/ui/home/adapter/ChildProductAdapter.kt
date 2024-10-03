package com.freshervnc.pharmacycounter.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemChildProductBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct

class ChildProductAdapter(private var datas : List<Data> , private val listener : OnClickItemProduct) :
    RecyclerView.Adapter<ChildProductAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemChildProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemChildProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(datas[position]){
                Glide.with(holder.itemView.context)
                    .load(this.imgUrl)
                    .error(R.drawable.ic_picture)
                    .into(binding.itemChildImageProduct)
                binding.itemChildTvNameProduct.text = this.name
                binding.itemChildTvBonusCoin.text = "Tặng ${this.bonusCoins} coin"
                binding.itemChildTvPack.text = this.pack
                binding.itemChildTvMinimum.text = "Số lượng tối thiểu "+this.minimumAmount.toString()
                binding.itemChildTvPrice.text = "${this.price} VND"
                binding.itemChildBtnAddCart.setOnClickListener {
                    listener.onClickItem(datas[position])
                }
            }
        }
    }

    fun setList(list : List<Data>){
        this.datas = list
        notifyDataSetChanged()
    }

}