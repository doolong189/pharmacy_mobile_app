package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse

class CartRepository {
    suspend fun requestAddToCart(authHeader : String, cart : RequestCartResponse ) = RetrofitInstance.api.addProductToCart(authHeader,cart)

    suspend fun requestGetCart(authHeader : String) = RetrofitInstance.api.getCart(authHeader)
}