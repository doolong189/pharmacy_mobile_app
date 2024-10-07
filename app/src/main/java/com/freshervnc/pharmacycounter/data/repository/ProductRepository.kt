package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.product.ProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse

class ProductRepository {

    suspend fun getProduct(authHeader : String, request : RequestProductResponse) = RetrofitInstance.api.getProduct(authHeader , request)
}