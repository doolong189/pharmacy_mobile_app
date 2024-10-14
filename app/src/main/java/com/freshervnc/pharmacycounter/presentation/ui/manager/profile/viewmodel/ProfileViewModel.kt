package com.freshervnc.pharmacycounter.presentation.ui.manager.profile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.ProfileRepository
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val application: Application) : AndroidViewModel(application){
    private val repository : ProfileRepository = ProfileRepository()

    fun getProfile(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getProfile(authHeader)))
            }else{
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.message?:application.getString(R.string.string_error)))
        }
    }

    fun getCustomerProfile(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getCustomerProfile(authHeader)))
            }else{
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,ex.message?:application.getString(R.string.string_error)))
        }
    }

    class ProfileViewModelFactory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                return ProfileViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_error))
            }
        }
    }
}