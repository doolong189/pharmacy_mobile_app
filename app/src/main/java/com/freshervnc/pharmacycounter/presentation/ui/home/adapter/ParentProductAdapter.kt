package com.freshervnc.pharmacycounter.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.databinding.ItemParentProductBinding
import com.freshervnc.pharmacycounter.domain.models.Product
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemHomePage
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemProduct

class ParentProductAdapter(private val listener : OnClickItemProduct , private val homeListener : OnClickItemHomePage) :
    RecyclerView.Adapter<ParentProductAdapter.HomeViewHolder>(){
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
                binding.itemParentTvCategory.text = this.name
                if (this.data.isEmpty()){
                    binding.itemParentLnEmptyProduct.visibility = View.VISIBLE
                    binding.itemParentRcChildProduct.visibility = View.GONE
                }else{
                    binding.itemParentLnEmptyProduct.visibility = View.GONE
                    binding.itemParentRcChildProduct.visibility = View.VISIBLE
                    val childAdapter: ChildProductAdapter = ChildProductAdapter(this.data,listener)
                    binding.itemParentRcChildProduct.setLayoutManager(GridLayoutManager(holder.itemView.context, 2))
                    binding.itemParentRcChildProduct.setAdapter(childAdapter)
                    binding.itemParentRcChildProduct.setHasFixedSize(true)
                    childAdapter.notifyDataSetChanged()

                    binding.itemParentImgMore.setOnClickListener {
                        homeListener.onClickItem(products[position])
                    }
                }
            }
        }
    }

    fun setList(list : List<Product>){
        this.products = list
        notifyDataSetChanged()
    }

}