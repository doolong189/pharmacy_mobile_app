package com.freshervnc.pharmacycounter.presentation.ui.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.LogoutRepository
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class ManagerViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = LogoutRepository()

    fun requestLogOut(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestLogOut(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.localizedMessage ?: application.getString(R.string.string_error)))
        }
    }

    class ManagerViewModelFactory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManagerViewModel::class.java))
            {
                return ManagerViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}