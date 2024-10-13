package com.freshervnc.pharmacycounter.domain.response.profile

import com.freshervnc.pharmacycounter.domain.models.Provinces
import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    val code : Int,
    var message : List<String>,
    var response : Response
)

data class Response(
    var ten : String,
    @SerializedName("ten_nha_thuoc")
    var tenNhaThuoc : String,
    @SerializedName("dia_chi")
    var diaChi : String,
    val email : String,
    var sdt : String,
    var tinh : Int ,
    @SerializedName("ma_so_thue")
    var maSoThue : String,
    var img :  String,
    @SerializedName("trang_thai")
    var trangThai : Int,
    var provinces : List<Provinces> // thanh pho
)