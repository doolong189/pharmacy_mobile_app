package com.freshervnc.pharmacycounter.domain.response.category

import com.freshervnc.pharmacycounter.domain.models.Category

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
