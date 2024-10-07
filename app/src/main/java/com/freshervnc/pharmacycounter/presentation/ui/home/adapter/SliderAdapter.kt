package com.freshervnc.pharmacycounter.presentation.ui.home.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.domain.response.homepage.Banner

class SliderAdapter(private val images: List<Banner>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
//        holder.imageView.setImageResource(images[position])
        val item = images[position]
        Glide.with(holder.itemView.context).load(item.value).error(R.drawable.ic_picture).into(holder.imageView)
        val url = "http://18.138.176.213/system/general_information/1"
        holder.itemView.setOnClickListener {
            val emptyBrowserIntent = Intent()
            emptyBrowserIntent.action = Intent.ACTION_VIEW
            emptyBrowserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
            emptyBrowserIntent.data = Uri.fromParts("http", "", null)
            val targetIntent = Intent()
            targetIntent.action = Intent.ACTION_VIEW
            targetIntent.addCategory(Intent.CATEGORY_BROWSABLE)
            targetIntent.data = Uri.parse(url)
            targetIntent.selector = emptyBrowserIntent
            holder.itemView.context.startActivity(targetIntent)
        }
    }

    override fun getItemCount(): Int = images.size
}