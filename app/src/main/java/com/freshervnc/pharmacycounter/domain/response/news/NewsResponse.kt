package com.freshervnc.pharmacycounter.domain.response.news

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    val code : Int,
    val message : List<String>,
    val response : Response
)
data class Response(
    @SerializedName("current_page")
    val currentPage : Int,
    val data : List<Data>
)

data class Data(
    val id : Int,
    @SerializedName("tieu_de")
    val tieuDe : String,
    @SerializedName("mo_ta")
    val moTa : String,
    val img : String ,
    @SerializedName("ngay_cong_khai")
    val ngayCongKhai : String,
    val url : String
)