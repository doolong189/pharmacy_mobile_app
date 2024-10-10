package com.freshervnc.pharmacycounter.presentation.ui.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.freshervnc.pharmacycounter.MainActivity
import com.freshervnc.pharmacycounter.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {
    private lateinit var binding : FragmentWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentWebviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPayment()
    }

    private fun requestPayment(){
        (activity as MainActivity).hideBottomNav()
        val b = arguments
        if (b != null) {
            val strUrlPayment = b.getInt("idPage")
            val urlString = "http://18.138.176.213/system/general_information/${strUrlPayment}"
            binding.paymentOnlineWebView.webViewClient = WebViewClient()
            // this will load the url of the website
            binding.paymentOnlineWebView.loadUrl(urlString)

            // this will enable the javascript settings, it can also allow xss vulnerabilities
            binding.paymentOnlineWebView.settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            binding.paymentOnlineWebView.settings.setSupportZoom(true)
        }
    }

}