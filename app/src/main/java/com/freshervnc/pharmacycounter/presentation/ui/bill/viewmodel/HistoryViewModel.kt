package com.freshervnc.pharmacycounter.presentation.ui.bill.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.HistoryRepository
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class HistoryViewModel(private val application: Application) : AndroidViewModel(application){
    private val repository : HistoryRepository = HistoryRepository()

    fun getHistory(authHeader : String , page : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getHistory(authHeader,page)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun detailBill(authHeader: String , request : RequestDetailBillResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.detailHistory(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    class HistoryViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
                return HistoryViewModel(application) as T
            }else{
                throw  IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}