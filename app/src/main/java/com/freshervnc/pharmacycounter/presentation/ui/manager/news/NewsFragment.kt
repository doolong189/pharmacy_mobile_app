package com.freshervnc.pharmacycounter.presentation.ui.manager.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentNewsBinding
import com.freshervnc.pharmacycounter.presentation.listener.OnClickItemNews
import com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.adapter.ContactAdapter
import com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.viewmodel.ContactViewModel
import com.freshervnc.pharmacycounter.presentation.ui.manager.news.adapter.NewsAdapter
import com.freshervnc.pharmacycounter.presentation.ui.manager.news.viewmodel.NewsViewModel
import com.freshervnc.pharmacycounter.utils.Status

class NewsFragment : Fragment(), OnClickItemNews {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter : NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initVariable()
    }


    private fun init(){
        (activity as MainActivity).hideBottomNav()
        newsViewModel = ViewModelProvider(this,
            NewsViewModel.NewsViewModelFactory(requireActivity().application))[NewsViewModel::class.java]
        adapter = NewsAdapter(this)
    }

    private fun initVariable() {
        binding.newsRcView.setHasFixedSize(true)
        binding.newsRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRcView.adapter = adapter
        getData()
    }

    private fun getData() {
        newsViewModel.getNews()
            .observe(viewLifecycleOwner) { it ->
                it?.let { resources ->
                    when (resources.status) {
                        Status.SUCCESS -> {
                            resources.data?.let { item ->
                                adapter.setList(item.response.data)
                            }
                        }
                        Status.ERROR -> {}
                        Status.LOADING -> {}
                    }
                }
            }
    }

    override fun onClickItemNews(url: String) {
        openLink(url)
    }
    private fun openLink(url : String){
        val emptyBrowserIntent = Intent()
        emptyBrowserIntent.action = Intent.ACTION_VIEW
        emptyBrowserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
        emptyBrowserIntent.data = Uri.fromParts("http", "", null)
        val targetIntent = Intent()
        targetIntent.action = Intent.ACTION_VIEW
        targetIntent.addCategory(Intent.CATEGORY_BROWSABLE)
        targetIntent.data = Uri.parse(url)
        targetIntent.selector = emptyBrowserIntent
        requireActivity().startActivity(targetIntent)
    }
}