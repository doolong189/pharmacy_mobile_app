package com.freshervnc.pharmacycounter.presentation.ui.bill.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.ItemHistoryBinding
import com.freshervnc.pharmacycounter.databinding.ItemProductConfirmPaymentBinding
import com.freshervnc.pharmacycounter.domain.response.history.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemHistory

class HistoryAdapter(private val listener : OnClickItemHistory) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var historiess: List<Data> = listOf()
    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        with(holder) {
            val item = historiess[position]
            binding.itemHistoryTvId.text = item.id.toString()
            binding.itemHistoryTvDate.text = item.createAt
            binding.itemHistoryTvAddress.text = item.address
            binding.itemHistoryTvTotalAmount.text = "${item.totalAmount} VND"

            if (item.status == 0){
                binding.itemHistoryTvStatus.setTextColor(Color.RED)
                binding.itemHistoryTvTotalAmount.setTextColor(Color.RED)
                binding.itemHistoryImgStatus.setImageResource(R.drawable.ic_payment_wait)
                binding.itemHistoryTvStatus.text = "Chờ thanh toán"
            }else{
                binding.itemHistoryTvStatus.setTextColor(Color.GREEN)
                binding.itemHistoryTvTotalAmount.setTextColor(Color.GREEN)
                binding.itemHistoryImgStatus.setImageResource(R.drawable.ic_payment_done)
                binding.itemHistoryTvStatus.text = "Đã thanh toán"
            }

            holder.itemView.setOnClickListener {
                listener.onClickItem(item.id,1)
            }
        }
    }

    override fun getItemCount(): Int {
        return historiess.size
    }

    fun setList(list: List<Data>) {
        this.historiess = list
        notifyDataSetChanged()
    }
}