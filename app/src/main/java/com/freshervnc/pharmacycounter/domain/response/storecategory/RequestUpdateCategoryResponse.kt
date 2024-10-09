package com.freshervnc.pharmacycounter.domain.response.storecategory

data class RequestUpdateCategoryResponse (
    val type : String,
    val id : Int,
    val name : String,
)