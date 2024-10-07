package com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.PaymentConfirmRepository
import com.freshervnc.pharmacycounter.domain.response.voucher.RequestVoucherResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class PaymentConfirmViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: PaymentConfirmRepository = PaymentConfirmRepository()

fun getVoucher(authHeader : String , dataId : List<Int>) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getVoucher(authHeader,dataId)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("zzz1", ""+ex.localizedMessage)
        }
    }

    class PaymentConfirmViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PaymentConfirmViewModel::class.java)){
                return PaymentConfirmViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}