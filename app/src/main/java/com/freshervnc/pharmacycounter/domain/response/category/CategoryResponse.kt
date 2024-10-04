package com.freshervnc.pharmacycounter.domain.response.category

data class CategoryResponse(
    val code: Int,
    val message: List<String>,
    val response: List<Response>
)

data class Response(
    val category: List<Category>,
    val icon: String,
    val key: String,
    val name: String
)

data class Category(
    val name: String,
    val value: Int
)