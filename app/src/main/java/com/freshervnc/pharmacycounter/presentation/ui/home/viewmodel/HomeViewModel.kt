package com.freshervnc.pharmacycounter.presentation.ui.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.HomeRepository
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : HomeRepository = HomeRepository()

    fun getHome(authHeader : String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getHomeRepository(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.message ?: application.getString(R.string.string_error)))
            Log.e("home", ex.localizedMessage!!.toString())
        }
    }

    fun getCustomerHome(authHeader : String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getCustomerHomePageRepository(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.message ?: application.getString(R.string.string_error)))
            Log.e("home", ex.localizedMessage!!.toString())
        }
    }

    class HomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
                return HomeViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}