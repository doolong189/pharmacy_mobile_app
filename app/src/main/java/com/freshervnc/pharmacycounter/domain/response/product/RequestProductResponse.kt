package com.freshervnc.pharmacycounter.domain.response.product

import com.google.gson.annotations.SerializedName

data class RequestProductResponse (
    val page : String = "",
    val category : String = "",
    val search : String = "",
    @SerializedName("hoat_chat")
    val activeIngredient : Int = 0,
    @SerializedName("nhom_thuoc")
    val typeMedicine : Int = 0,
    @SerializedName("nha_san_xuat")
    val manufacturer : Int = 0,
    val hashtag : Int = 0,
)