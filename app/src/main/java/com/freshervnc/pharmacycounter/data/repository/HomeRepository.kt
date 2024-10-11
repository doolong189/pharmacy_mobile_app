package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class HomeRepository {

    suspend fun getHomeRepository(authHeader : String) = RetrofitInstance.api.getHomePage(authHeader)

    suspend fun getCustomerHomePageRepository(authHeader: String) = RetrofitInstance.api.getCustomerHomePage(authHeader)
}