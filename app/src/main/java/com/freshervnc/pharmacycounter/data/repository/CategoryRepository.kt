package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestCreateCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestDeleteCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestUpdateCategoryResponse

class CategoryRepository {
    suspend fun requestGetCategory(authHeader : String) = RetrofitInstance.api.getCategory(authHeader)

    suspend fun requestGetStoreCategory(authHeader : String) = RetrofitInstance.api.getStoreCategory(authHeader)

    suspend fun requestGetCategoryType(authHeader: String , request : RequestCategoryTypeResponse) = RetrofitInstance.api.getCategoryType(authHeader,request)

    suspend fun requestGetStoreCategoryType(authHeader: String , request : RequestCategoryTypeResponse) = RetrofitInstance.api.getStoreCategoryType(authHeader,request)

    suspend fun requestCreateStoreCategoryType(authHeader: String , request : RequestCreateCategoryResponse) = RetrofitInstance.api.getCreateStoreCategoryType(authHeader,request)

    suspend fun requestUpdateStoreCategoryType(authHeader: String , request: RequestUpdateCategoryResponse) = RetrofitInstance.api.getUpdateStoreCategoryType(authHeader,request)

    suspend fun requestDeleteStoreCategoryType(authHeader: String, request : RequestDeleteCategoryResponse) = RetrofitInstance.api.getDeleteStoreCategoryType(authHeader,request)
}