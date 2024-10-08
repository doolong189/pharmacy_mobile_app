package com.freshervnc.pharmacycounter.domain.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    val code : Int,
    val message : List<String>,
    val response : List<Response>
)

data class Response(
    val id : Int,
    @SerializedName("ten_san_pham")
    val tenSanPham : String,
    @SerializedName("ban_chay")
    val banChay : Int,
    @SerializedName("khuyen_mai")
    val khuyenMai : Int,
)