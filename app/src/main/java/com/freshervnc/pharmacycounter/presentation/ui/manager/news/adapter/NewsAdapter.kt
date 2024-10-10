package com.freshervnc.pharmacycounter.presentation.ui.manager.news.adapter

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemNewsBinding
import com.freshervnc.pharmacycounter.domain.response.news.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemNews

class NewsAdapter(val listener : OnClickItemNews) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var news_list: List<Data> = listOf()
    inner class NewsViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder) {
            val item = news_list[position]

            Glide.with(holder.itemView.context).load(item.img).error(R.drawable.ic_picture)
                .into(binding.itemNewsImgView)

            binding.itemNewsTvtitle.text = item.tieuDe
            binding.itemNewsTvDescription.text = item.moTa
            binding.itemNewsTvDate.text = item.ngayCongKhai
            val str = "Bấm vào đây >>"
            val spannableString = SpannableString(str)
            spannableString.setSpan(UnderlineSpan(), 0, str.length, 0)
            binding.itemNewsTvValue.text = spannableString
            binding.itemNewsTvValue.setTextColor(Color.BLUE)
            binding.itemNewsTvValue.typeface = Typeface.DEFAULT_BOLD
            //
            binding.itemNewsTvValue.setOnClickListener {
                listener.onClickItemNews(item.url)
            }
        }
    }

    override fun getItemCount(): Int {
        return news_list.size
    }

    fun setList(list: List<Data>) {
        this.news_list = list
        notifyDataSetChanged()
    }
}