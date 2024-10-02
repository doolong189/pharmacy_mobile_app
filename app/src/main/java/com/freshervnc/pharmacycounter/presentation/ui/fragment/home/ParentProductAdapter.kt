package com.freshervnc.pharmacycounter.presentation.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.databinding.ItemParentProductBinding
import com.freshervnc.pharmacycounter.domain.response.homepage.Product

class ParentProductAdapter() :
    RecyclerView.Adapter<ParentProductAdapter.HomeViewHolder>() {
    private var products : List<Product> = listOf()

    inner class HomeViewHolder(val binding : ItemParentProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemParentProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(products[position]){
                binding.itemParentTvDiscount.text = this.name
                val childAdapter: ChildProductAdapter = ChildProductAdapter(this.data)
                binding.itemParentRcChildProduct.setLayoutManager(GridLayoutManager(holder.itemView.context, 2))
                binding.itemParentRcChildProduct.setAdapter(childAdapter)
                binding.itemParentRcChildProduct.setHasFixedSize(true)
                childAdapter.notifyDataSetChanged()
            }
        }
    }

    fun setList(list : List<Product>){
        this.products = list
        notifyDataSetChanged()
    }

}