package com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.adapter

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
import com.freshervnc.pharmacycounter.databinding.ItemContactBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductCartBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.response.bill.Data
import com.freshervnc.pharmacycounter.domain.response.contact.Response
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemCart
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemContact
import com.freshervnc.pharmacycounter.presentation.ui.home.adapter.TagAdapter

class ContactAdapter(val listener : OnClickItemContact) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts: List<Response> = listOf()
    inner class ContactViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder) {
            val item = contacts[position]

            Glide.with(holder.itemView.context).load(item.icon).error(R.drawable.ic_picture)
                .into(binding.itemContactImgView)

            binding.itemContactTvName.text = item.name
            val str = "Bấm vào đây"
            val spannableString = SpannableString(str)
            spannableString.setSpan(UnderlineSpan(), 0, str.length, 0)
            binding.itemContactTvValue.text = spannableString
            binding.itemContactTvValue.setTextColor(Color.BLUE)
            binding.itemContactTvValue.typeface = Typeface.DEFAULT_BOLD
            //
            binding.itemContactTvValue.setOnClickListener {
                listener.onClickItem(item.value, item.type)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun setList(list: List<Response>) {
        this.contacts = list
        notifyDataSetChanged()
    }
}