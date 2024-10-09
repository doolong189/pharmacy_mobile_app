package com.freshervnc.pharmacycounter.presentation.ui.product.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.ProductRepository
import com.freshervnc.pharmacycounter.domain.response.product.RequestDeleteProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class ProductViewModel(private val application: Application) : AndroidViewModel(application){

    private val repository : ProductRepository = ProductRepository()

    fun getProduct(authHeader : String ,request : RequestProductResponse)  = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getProduct(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("log product o day",ex.localizedMessage.toString())
        }
    }

    fun getStoreProduct(authHeader : String ,request : RequestProductResponse)  = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.getStoreProduct(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("log product o day", ex.localizedMessage!!.toString())
        }
    }

    fun deleteProduct(authHeader : String ,request : RequestDeleteProductResponse)  = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.deleteProduct(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))

        }
    }

    class ProductViewModelFactory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
                return ProductViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}