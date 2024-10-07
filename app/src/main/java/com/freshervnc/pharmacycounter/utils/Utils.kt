package com.freshervnc.pharmacycounter.utils

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.util.Log

object Utils {
    fun hasInternetConnection(application: Application):Boolean{
        val connectivityManager=application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            Log.d(ContentValues.TAG,"SDK_M")
            val activeNetwork=connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else ->false
            }
        }else {
            Log.d(ContentValues.TAG,"SDK_N")
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun buildPaymentOnline(maDonHang : String , totalPrice : String , email : String, sdt : String) : String{
        val URL_PAYMENT = "http://18.138.176.213/agency/megapay/va?ma_don_hang=${maDonHang}&total_price=${totalPrice}&email=${email}&sdt=${sdt}"
        return URL_PAYMENT
    }

    fun buildUrl(maDonHang: String, totalPrice: Int, email: String, sdt: String): String {
        val baseUrl = "http://18.138.176.213/agency/megapay/va"

        return Uri.parse(baseUrl).buildUpon()
            .appendQueryParameter("ma_don_hang", maDonHang)
            .appendQueryParameter("total_price", totalPrice.toString())
            .appendQueryParameter("email", email)
            .appendQueryParameter("sdt", sdt)
            .build()
            .toString()
    }
}