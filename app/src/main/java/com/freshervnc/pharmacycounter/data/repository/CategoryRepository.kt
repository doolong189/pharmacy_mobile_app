package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse

class CategoryRepository {
    suspend fun requestGetCategory(authHeader : String) = RetrofitInstance.api.getCategory(authHeader)

    suspend fun requestGetCategoryType(authHeader: String , request : RequestCategoryTypeResponse) = RetrofitInstance.api.getCategoryType(authHeader,request)

}