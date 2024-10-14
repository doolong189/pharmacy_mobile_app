package com.freshervnc.pharmacycounter.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.databinding.ItemChildCategoryBinding
import com.freshervnc.pharmacycounter.domain.models.Category
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory

class CategoryTypeAdapter(private var categorytypes : List<Category>, private val listener : OnClickItemCategory) :
    RecyclerView.Adapter<CategoryTypeAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(val binding : ItemChildCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemChildCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categorytypes.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(categorytypes[position]){
                binding.itemChildCategoryTvName.text = this.name
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

}