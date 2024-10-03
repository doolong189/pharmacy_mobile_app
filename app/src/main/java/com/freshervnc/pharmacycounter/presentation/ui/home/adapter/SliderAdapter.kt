package com.freshervnc.pharmacycounter.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.freshervnc.pharmacycounter.R

class SliderAdapter(private val images: List<Int>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
//        val item = images[position]
//        Glide.with(holder.itemView.context).load(item.value).error(R.drawable.ic_picture).into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}