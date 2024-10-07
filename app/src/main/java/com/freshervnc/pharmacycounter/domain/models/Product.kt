package com.freshervnc.pharmacycounter.domain.models


data class Product(
    val key: String,
    val value: String,
    val name: String,
    val data : List<Data>
)