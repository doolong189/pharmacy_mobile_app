package com.freshervnc.pharmacycounter.domain.models

import com.freshervnc.pharmacycounter.domain.response.homepage.Tag
import com.freshervnc.pharmacycounter.domain.response.product.HoaChatSanPhams
import com.freshervnc.pharmacycounter.domain.response.product.ImageSanPhams
import com.freshervnc.pharmacycounter.domain.response.product.ProductTags
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("gio_hang_id")
    val gioHangId : Int,
    @SerializedName("id_member")
    val memberId : Int,
    val id : Int,
    @SerializedName("khuyen_mai")
    val discount: Int,
    @SerializedName("ten_san_pham")
    val name: String,
    @SerializedName("quy_cach_dong_goi")
    val pack: String,
    @SerializedName("so_luong")
    var quality: Int,
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
    @SerializedName("discount_price")
    val discountPrice : Int,
    @SerializedName("detail_url")
    val detailUrl : String,
    var tags : List<Tag>,
    var imgSanPhams : List<ImageSanPhams>,
    var productTags : List<ProductTags>,
    val hoaChatSanPhams : List<HoaChatSanPhams>,

    //store _ product
    @SerializedName("ban_chay")
    var banChay : Int,
    @SerializedName("trang_thai")
    var trangThai : Int,
)