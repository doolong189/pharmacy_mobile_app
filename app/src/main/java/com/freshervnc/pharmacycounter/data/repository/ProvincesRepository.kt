package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class ProvincesRepository {

    suspend fun getProvinces() = RetrofitInstance.api.getProvinces()
}