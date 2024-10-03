package com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.CartRepository
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : CartRepository = CartRepository()

    fun addToCart(authHeader : String , cart : RequestCartResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestAddToCart(authHeader,cart)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null, ex.message ?: application.getString(R.string.string_error)))
        }
    }

    fun getCart(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetCart(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null, ex.message ?: application.getString(R.string.string_error)))
        }
    }

    class CartViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)){
                return CartViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_error))
            }
        }
    }
}