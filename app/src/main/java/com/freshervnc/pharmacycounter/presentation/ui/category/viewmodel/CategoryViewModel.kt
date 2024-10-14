package com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.CategoryRepository
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestCreateCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestDeleteCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestUpdateCategoryResponse
import com.freshervnc.pharmacycounter.presentation.ui.cart.viewmodel.CartViewModel
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class CategoryViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : CategoryRepository = CategoryRepository()

    fun getCategory(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetCategory(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getCustomerCategory(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetCustomerCategory(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getStoreCategory(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetStoreCategory(authHeader)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getCategoryType(authHeader : String  , request : RequestCategoryTypeResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getCustomerCategoryType(authHeader : String  , request : RequestCategoryTypeResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetCustomerCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getStoreCategoryType(authHeader : String  , request : RequestCategoryTypeResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestGetStoreCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }

    fun getCreateStoreCategoryType(authHeader : String  , request : RequestCreateCategoryResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestCreateStoreCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
        }
    }


    fun getUpdateStoreCategoryType(authHeader : String  , request : RequestUpdateCategoryResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestUpdateStoreCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("update",ex.localizedMessage!!.toString())
        }
    }


    fun getDeleteStoreCategoryType(authHeader : String  , request : RequestDeleteCategoryResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.requestDeleteStoreCategoryType(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }
        catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("delete",ex.localizedMessage!!.toString())
        }
    }

    class CategoryViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
                return CategoryViewModel(application) as T
            }else{
                throw IllegalArgumentException(application.getString(R.string.string_error))
            }
        }
    }
}