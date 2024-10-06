package com.freshervnc.pharmacycounter.domain.response.cart

import com.freshervnc.pharmacycounter.domain.models.Data
import com.google.gson.annotations.SerializedName

data class CartResponse (
    val code : Int,
    val message : List<String>,
    val response : Response
)

data class Response(
    val products: Product,
    @SerializedName("total_number")
    val totalNumber : Int,
    @SerializedName("total_price")
    val totalPrice : Int,
    @SerializedName("ti_le_giam")
    val discount : String
)

data class Product(
    @SerializedName("current_page")
    val currentPage : Int,
    val data : List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl : String,
    val from : Int,
    @SerializedName("last_page")
    val lastPage : Int,
    @SerializedName("last_page_url")
    val lastPageUrl : String,
    @SerializedName("next_page_url")
    val nextPageUrl : String,
    val path : String,
    @SerializedName("per_page")
    val perPage : Int,
    @SerializedName("prev_page_url")
    val prevPageUrl : String,
    val to : Int,
    val total : Int
)