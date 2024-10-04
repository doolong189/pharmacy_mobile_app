package com.freshervnc.pharmacycounter.presentation.ui.registration.register.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.RegisterRepository
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository: RegisterRepository = RegisterRepository()

//    fun requestRegisterCounter(response: RequestRegisterCounter) = liveData(Dispatchers.IO){
//        try {
//            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
//                emit(Resource.success(repository.requestRegisterCounter(response)))
//            }else{
//                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
//            }
//        }catch (ex : Exception){
//            emit(Resource.error(null,ex.message ?: application.getString(R.string.string_error)))
//        }
//    }

    ///new
    fun requestRegisterCounter(fullNameBody: RequestBody, nameCounter: RequestBody, address: RequestBody, provinces: RequestBody, phone: RequestBody, email: RequestBody, password: RequestBody, img: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(Resource.success(
                    repository.requestRegisterCounter(fullNameBody, nameCounter, address, provinces, phone, email, password, img))
                )
            } else {
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: application.getString(R.string.string_error)))
        }
    }


    fun requestRegisterCustomer(
        fullNameBody: RequestBody,
        idAgency: RequestBody,
        address: RequestBody,
        provinces: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        password: RequestBody,
        img: MultipartBody.Part
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(
                    Resource.success(
                        repository.requestRegisterCustomer(
                            fullNameBody,
                            idAgency,
                            address,
                            provinces,
                            phone,
                            email,
                            password,
                            img
                        )
                    )
                )
            } else {
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: application.getString(R.string.string_error)))
        }
    }

    class RegisterViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(application) as T
            } else {
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}