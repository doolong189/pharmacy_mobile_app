package com.freshervnc.pharmacycounter.domain.response.category

data class RequestCategoryTypeResponse (
    val type: String ,
    val page : Int = 0,
    val search : String = ""
)