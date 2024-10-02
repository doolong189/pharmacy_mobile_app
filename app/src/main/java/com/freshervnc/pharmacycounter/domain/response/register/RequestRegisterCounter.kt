package com.freshervnc.pharmacycounter.domain.response.register

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class RequestRegisterCounter(
    @SerializedName("ten")
    val name:String,
    @SerializedName("ten_nha_thuoc")
    val nameCounter: String,
    @SerializedName("dia_chi")
    val address: String,
    @SerializedName("tinh")
    val provinces: String,
    @SerializedName("sdt")
    val phone: String,
    val email: String,
    val password: String,
    val img: MultipartBody.Part
)