package com.freshervnc.pharmacycounter.presentation.ui.manager.categorytype.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.databinding.ItemChildCategoryBinding
import com.freshervnc.pharmacycounter.databinding.ItemStoreCategoryTypeBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory

class StoreCategoryTypeAdapter(private val listener : OnClickItemCategory) :
    RecyclerView.Adapter<StoreCategoryTypeAdapter.HomeViewHolder>() {
    private var categorytypes : List<Category> = listOf()
    inner class HomeViewHolder(val binding : ItemStoreCategoryTypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemStoreCategoryTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categorytypes.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(categorytypes[position]){
                binding.itemChildCategoryTvName.text = this.name
                binding.itemChildCategoryTvSoLuong.text = this.size.toString()
                holder.itemView.setOnClickListener {
                    listener.onClickItem(categorytypes[position],"")
                }
            }
        }
    }

    fun setList(list : List<Category>){
        this.categorytypes = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Category {
        return categorytypes[position]
    }
}