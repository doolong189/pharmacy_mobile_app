package com.freshervnc.pharmacycounter.domain.response.product

import com.freshervnc.pharmacycounter.domain.models.Data


data class ProductResponse (
    val code : Int,
    val message : List<String>,
    val response : Response
)

data class Response(
    val currentPage : Int,
    val data : List<Data>
)

data class ImageSanPhams(
    val id : Int,
    val idSanPham : Int,
    val img : String,
    val createdAt : String,
    val updateAt : String
)

data class ProductTags(
    val id : Int,
    val key : String,
    val value : Int,
    val name: String
)

data class HoaChatSanPhams(
    val id : Int,
    val idSanPham : Int,
    val idHoatChat : Int,
    val hamLuong : String,
    val createdAt : String,
    val updatedAt : String,
    val deleteAt: String
)

