package com.freshervnc.pharmacycounter.domain.response.bill

import com.google.gson.annotations.SerializedName

data class DetailBillResponse (
    val code : Int ,
    val message : List<String> ,
    val response : DetailResponse,
)

data class DetailResponse(
    val products : Products,
    @SerializedName("total_products")
    val totalProduct: Int,
    @SerializedName("date_time")
    val dateTime : String,
    @SerializedName("total_price")
    val totalPrice : Int,
    val price : Int,
)

data class Products(
    @SerializedName("current_page")
    val currentPage : Int,
    val data : List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl : String,
    val from : Int,
    @SerializedName("last_page")
    val lastPage : Int,
    @SerializedName("last_page_url")
    val lastPageUrl : String,
    @SerializedName("next_page_url")
    val nextPageUrl : Int,
    val path : String,
    @SerializedName("per_page")
    val perPage:Int,
    @SerializedName("perv_page_url")
    val pervPageUrl : Int,
    val to: Int,
    val total : Int
)

data class Data(
    @SerializedName("so_luong")
    val soLuong : Int,
    @SerializedName("don_gia")
    val donGia: Int,
    @SerializedName("discount_price")
    val discountPrice : Int,
    @SerializedName("bonus_coins")
    val bonusCoins : Int,
    val id : Int,
    @SerializedName("ten_san_pham")
    val tenSanPham : String,
    @SerializedName("img_url")
    val imgUrl : String ,
    @SerializedName("khuyen_mai")
    val khuyenMai : Int,
    @SerializedName("detail_url")
    val detailUrl : String,
    val tags: List<Tags>
)

data class Tags(
    val key : String,
    val value: Int,
    val name : String
)