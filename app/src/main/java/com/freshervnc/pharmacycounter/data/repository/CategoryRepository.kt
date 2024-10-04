package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class CategoryRepository {
    suspend fun requestGetCategory(authHeader : String) = RetrofitInstance.api.getCategory(authHeader)
}