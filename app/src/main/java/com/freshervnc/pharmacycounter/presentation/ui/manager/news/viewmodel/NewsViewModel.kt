package com.freshervnc.pharmacycounter.presentation.ui.manager.news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.ContactRepository
import com.freshervnc.pharmacycounter.data.repository.NewsRepository
import com.freshervnc.pharmacycounter.data.repository.PaymentConfirmRepository
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.viewmodel.PaymentConfirmViewModel
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class NewsViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository()

    fun getNews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(Resource.success(repository.getNews()))
            } else {
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        } catch (ex: Exception) {
            emit(Resource.error(null, application.getString(R.string.string_error)))
            Log.e("zzz1", "" + ex.localizedMessage)
        }
    }

    class NewsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                return NewsViewModel(application) as T
            } else {
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}