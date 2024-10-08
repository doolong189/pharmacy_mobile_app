package com.freshervnc.pharmacycounter.presentation.ui.searchproduct.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.SearchRepository
import com.freshervnc.pharmacycounter.domain.response.search.RequestSearchResponse
import com.freshervnc.pharmacycounter.domain.response.search.SearchResponse
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository : SearchRepository = SearchRepository()

    fun searchProduct(authHeader : String , request : RequestSearchResponse) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())){
                emit(Resource.success(repository.searchProduct(authHeader,request)))
            }else{
                emit(Resource.error(null,application.getString(R.string.string_not_internet)))
            }
        }catch (ex : Exception){
            emit(Resource.error(null,application.getString(R.string.string_error)))
            Log.e("search error",ex.localizedMessage.toString())
        }
    }

    class SearchViewModelFactory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
                return SearchViewModel(application) as T
            }else{
                throw  IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}