package com.freshervnc.pharmacycounter.domain.response.homepage

import com.freshervnc.pharmacycounter.domain.models.Product
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


data class Tag(
    val key : String,
    val value : Int,
    val name: String
)

