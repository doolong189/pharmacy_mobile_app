package com.freshervnc.pharmacycounter.domain.response.homepage

import com.google.gson.annotations.SerializedName

data class HomePageResponse(
    val code : Int,
    val message : List<String>,
    val response: Response,
    @SerializedName("total_cart")
    val totalCart : Int,
    @SerializedName("total_notifications")
    val totalNotification: Int,
    @SerializedName("member_name")
    val memberName : String,
    @SerializedName("member_status")
    val memberStatus: Int,
    @SerializedName("thu_hang_icon")
    val rankIcon: String,
)

data class Response(
    val banners : List<Banner>,
    val events: List<Event>,
    @SerializedName("event_icon")
    val eventIcon : EventIcon,
    val products: List<Product>
)

data class Banner(
    val key : String,
    val value : String,
)

data class Event(
    val key : String,
    val value : String,
)

data class EventIcon(
    val image : String,
    val url : String,
    val badge : Int,
)

data class Product(
    val key: String,
    val value: String,
    val name: String,
    val data : List<Data>
)

data class Data(
    val id : Int,
    @SerializedName("khuyen_mai")
    val discount: Int,
    @SerializedName("ten_san_pham")
    val name: String,
    @SerializedName("quy_cach_dong_goi")
    val pack: String,
    @SerializedName("so_luong")
    val amount: Int,
    @SerializedName("don_gia")
    val price : Int,
    @SerializedName("bonus_coins")
    val bonusCoins : Int,
    @SerializedName("so_luong_toi_thieu")
    val minimumAmount : Int,
    @SerializedName("so_luong_toi_da")
    val maxAmount: Int,
    @SerializedName("img_url")
    val imgUrl : String,
    val discountPrice : Int,
    val detailUrl : String,
    val tags : List<Tag>
)

data class Tag(
    val key : String,
    val value : Int,
    val name: String
)

