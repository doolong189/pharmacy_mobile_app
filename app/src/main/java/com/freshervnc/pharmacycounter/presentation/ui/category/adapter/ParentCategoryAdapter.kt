package com.freshervnc.pharmacycounter.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemCategoryBinding
import com.freshervnc.pharmacycounter.databinding.ItemParentProductBinding
import com.freshervnc.pharmacycounter.domain.models.Product
import com.freshervnc.pharmacycounter.domain.response.category.Response
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCategory
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemParentCategory
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct

class ParentCategoryAdapter(private val listener : OnClickItemCategory , private val listenerParent : OnClickItemParentCategory) :
    RecyclerView.Adapter<ParentCategoryAdapter.HomeViewHolder>(){
    private var lists : List<Response> = listOf()

    inner class HomeViewHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(lists[position]){
                binding.itemCategoryTvName.text = this.name
                Glide.with(holder.itemView.context).load(lists[position].icon).error(R.drawable.ic_picture).into(binding.itemCategoryImgView)
                val childAdapter: ChildCategoryAdapter = ChildCategoryAdapter(listener)
                childAdapter.setList(this.category)
                binding.itemCategoryRcChildCategory.setLayoutManager(LinearLayoutManager(holder.itemView.context))
                binding.itemCategoryRcChildCategory.setAdapter(childAdapter)
                binding.itemCategoryRcChildCategory.setHasFixedSize(true)
                childAdapter.notifyDataSetChanged()

                binding.itemCategoryTvName.setOnClickListener {
                    listenerParent.onClickItem(lists[position].key)
                }
            }
        }
    }

    fun setList(list : List<Response>){
        this.lists = list
        notifyDataSetChanged()
    }

}