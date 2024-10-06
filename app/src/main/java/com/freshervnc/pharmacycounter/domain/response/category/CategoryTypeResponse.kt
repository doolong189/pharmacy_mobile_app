package com.freshervnc.pharmacycounter.domain.response.category

import com.freshervnc.pharmacycounter.domain.models.Category
import com.google.gson.annotations.SerializedName

data class CategoryTypeResponse (
    val code : Int,
    val message : List<String>,
    val response : ResponseType
)

data class ResponseType(
    @SerializedName("current_page")
    val currentPage : Int,
    val data : List<Category>,
    val firstPageUrl : String,
    val from:Int,
    val lastPage : Int,
    val lastPageUrl : String,
    val nextPageUrl : String,
    val path: String,
    val perPage : Int,
    val pervPageUrl : String,
    val to : Int,
    val total: Int
)
