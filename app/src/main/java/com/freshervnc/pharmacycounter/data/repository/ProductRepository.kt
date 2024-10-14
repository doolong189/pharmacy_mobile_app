package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.product.ProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestDeleteProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestShowProduct

class ProductRepository {

    suspend fun getProduct(authHeader : String, request : RequestProductResponse) = RetrofitInstance.api.getProduct(authHeader , request)
    suspend fun getStoreProduct(authHeader : String, request : RequestProductResponse) = RetrofitInstance.api.getStoreProduct(authHeader , request)
    suspend fun getShowProduct(authHeader : String , id : RequestShowProduct) = RetrofitInstance.api.getShowProduct(authHeader,id)
    suspend fun getBestSellerProduct(authHeader : String , id : RequestShowProduct) = RetrofitInstance.api.getBestSellerProduct(authHeader,id)
    suspend fun deleteProduct(authHeader: String, request : RequestDeleteProductResponse) = RetrofitInstance.api.deleteProduct(authHeader,request)
}