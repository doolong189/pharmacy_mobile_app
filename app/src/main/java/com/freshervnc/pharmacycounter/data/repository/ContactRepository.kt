package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class ContactRepository {
    suspend fun getContact() = RetrofitInstance.api.getContact()
    suspend fun getCustomerContact(authHeader : String) = RetrofitInstance.api.getCustomerContact(authHeader)
}