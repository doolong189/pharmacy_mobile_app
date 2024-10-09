package com.freshervnc.pharmacycounter.domain.response.product

import com.google.gson.annotations.SerializedName

data class RequestDeleteProductResponse (
    @SerializedName("id")
    val id : Int
)