package com.freshervnc.pharmacycounter.presentation.ui.bill.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentSalesHistoryBinding
import com.freshervnc.pharmacycounter.domain.response.history.Data
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemHistory
import com.freshervnc.pharmacycounter.presentation.ui.bill.adapter.HistoryAdapter
import com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel.HistoryViewModel
import com.freshervnc.pharmacycounter.utils.SharedPrefer
import com.freshervnc.pharmacycounter.utils.Status


class SalesHistoryFragment : Fragment() , OnClickItemHistory{
    private lateinit var binding: FragmentSalesHistoryBinding
    private lateinit var salesHistoryAdapter : HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var mySharedPrefer: SharedPrefer
    private var current_page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSalesHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
    }
    private fun init(){
        salesHistoryAdapter = HistoryAdapter(this)
        historyViewModel = ViewModelProvider(this,HistoryViewModel.HistoryViewModelFactory(requireActivity().application))[HistoryViewModel::class.java]
        mySharedPrefer = SharedPrefer(requireContext())
    }

    private fun initVariable(){
        binding.salesRcHistory.setHasFixedSize(true)
        binding.salesRcHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.salesRcHistory.adapter = salesHistoryAdapter
        getData()
        binding.dialogHistorySwLoading.setOnRefreshListener {
            getData()
        }
    }

    private fun getData(){
        historyViewModel.getHistory("Bearer " + mySharedPrefer.token,current_page).observe(viewLifecycleOwner,
            Observer { it ->
                it?.let { resources ->
                    when(resources.status){
                        Status.SUCCESS -> {
                            binding.dialogHistorySwLoading.isRefreshing = false
                            resources.data.let { item ->
                                val listTemp = mutableListOf<Data>()
                                for (x in item!!.response.data){
                                    if (x.status == 1){
                                        listTemp.add(x)
                                    }
                                }
                                if (listTemp.isEmpty()){
                                    binding.dialogHistoryLnEmpty.visibility = View.VISIBLE
                                }else {
                                    salesHistoryAdapter.setList(listTemp)
                                    salesHistoryAdapter.notifyDataSetChanged()
                                    binding.dialogHistoryLnEmpty.visibility = View.GONE
                                }
                            }
                        }
                        Status.ERROR -> {
                            binding.dialogHistorySwLoading.isRefreshing = false
                            binding.dialogHistoryLnEmpty.visibility = View.VISIBLE
                        }
                        Status.LOADING ->{
                            binding.dialogHistorySwLoading.isRefreshing = true
                        }
                    }
                }
            })
    }

    override fun onClickItem(id: Int, page: Int) {
        val args = Bundle()
        args.putInt("id", id)
        args.putInt("page",page)
        val newFragment: DetailHistoryFragment = DetailHistoryFragment()
        newFragment.setArguments(args)
        (activity as MainActivity).replaceFragment(newFragment)
    }
}