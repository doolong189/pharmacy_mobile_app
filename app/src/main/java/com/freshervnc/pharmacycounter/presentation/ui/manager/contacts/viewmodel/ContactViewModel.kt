package com.freshervnc.pharmacycounter.presentation.ui.manager.contacts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.freshervnc.pharmacycounter.R
import com.freshervnc.pharmacycounter.application.MyApplication
import com.freshervnc.pharmacycounter.data.repository.ContactRepository
import com.freshervnc.pharmacycounter.data.repository.PaymentConfirmRepository
import com.freshervnc.pharmacycounter.presentation.ui.confirmpayment.viewmodel.PaymentConfirmViewModel
import com.freshervnc.pharmacycounter.utils.Resource
import com.freshervnc.pharmacycounter.utils.Utils
import kotlinx.coroutines.Dispatchers

class ContactViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository = ContactRepository()

    fun getContact() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(Resource.success(repository.getContact()))
            } else {
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        } catch (ex: Exception) {
            emit(Resource.error(null, application.getString(R.string.string_error)))
            Log.e("zzz1", "" + ex.localizedMessage)
        }
    }

    fun getCustomerContact(authHeader : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                emit(Resource.success(repository.getCustomerContact(authHeader)))
            } else {
                emit(Resource.error(null, application.getString(R.string.string_not_internet)))
            }
        } catch (ex: Exception) {
            emit(Resource.error(null, application.getString(R.string.string_error)))
            Log.e("zzz1", "" + ex.localizedMessage)
        }
    }

    class ContactViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
                return ContactViewModel(application) as T
            } else {
                throw IllegalArgumentException(application.getString(R.string.string_not_found_view_model))
            }
        }
    }
}