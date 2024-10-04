package com.freshervnc.pharmacycounter.presentation.ui.category.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.CategoryRepository
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