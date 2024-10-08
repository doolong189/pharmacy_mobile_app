package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.search.RequestSearchResponse

class SearchRepository {
    suspend fun searchProduct(authHeader : String, request : RequestSearchResponse) = RetrofitInstance.api.searchProduct(authHeader,request)
}