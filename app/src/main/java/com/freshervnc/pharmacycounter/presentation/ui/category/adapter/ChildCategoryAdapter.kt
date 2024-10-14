package com.freshervnc.pharmacycounter.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemChildCategoryBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory

class ChildCategoryAdapter( private val listener : OnClickItemCategory , val key : String) :
    RecyclerView.Adapter<ChildCategoryAdapter.HomeViewHolder>() {
    private var categorys : List<Category> = mutableListOf()
    inner class HomeViewHolder(val binding : ItemChildCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemChildCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categorys.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(categorys[position]){
                binding.itemChildCategoryTvName.text = this.name
                holder.itemView.setOnClickListener {
                    listener.onClickItem(categorys[position],key)
                }
            }
        }
    }

    fun setList(list : List<Category>){
        this.categorys = list
        notifyDataSetChanged()
    }

}