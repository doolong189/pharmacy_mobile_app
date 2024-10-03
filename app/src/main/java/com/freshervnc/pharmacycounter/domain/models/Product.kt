package com.freshervnc.pharmacycounter.domain.models

import com.freshervnc.pharmacycounter.domain.response.homepage.Data

data class Product(
    val key: String,
    val value: String,
    val name: String,
    val data : List<Data>
)