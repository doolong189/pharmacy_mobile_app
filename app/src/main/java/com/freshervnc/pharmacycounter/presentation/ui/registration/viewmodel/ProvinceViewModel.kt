package com.freshervnc.pharmacycounter.presentation.ui.registration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.ProvincesRepository
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class ProvinceViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : ProvincesRepository = ProvincesRepository()

    fun getProvinces() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getProvinces()))
            }else{
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.message ?: application.getString(R.string.string_error)))
        }
    }

    class ProvinceViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProvinceViewModel::class.java)){
                return ProvinceViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}