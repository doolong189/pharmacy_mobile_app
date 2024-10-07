package com.freshervnc.pharmacycounter.presentation.ui.paymentonline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.databinding.FragmentPaymentOnlineBinding
import com.freshervnc.pharmacycounter.utils.Utils

class PaymentOnlineFragment : Fragment() {
    private lateinit var binding : FragmentPaymentOnlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentPaymentOnlineBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPayment()
    }

    private fun requestPayment(){
        val b = arguments
        if (b != null) {
            val strUrlPayment = b.getString("url_payment")
            binding.paymentOnlineWebView.webViewClient = WebViewClient()

            // this will load the url of the website
            binding.paymentOnlineWebView.loadUrl(strUrlPayment.toString())

            // this will enable the javascript settings, it can also allow xss vulnerabilities
            binding.paymentOnlineWebView.settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            binding.paymentOnlineWebView.settings.setSupportZoom(true)
        }
    }

}