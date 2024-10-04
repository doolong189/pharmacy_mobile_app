package com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.BillRepository
import com.freshervnc.pharmacycounter.domain.response.bill.RequestBillResponse
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class BillViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : BillRepository = BillRepository()

    fun createBill(authHeader: String, request: RequestBillResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(Resource.success(repository.createBill(authHeader, request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }


    class BillViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BillViewModel::class.java)){
                return BillViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }

    }
}