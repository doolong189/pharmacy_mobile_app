package com.freshervnc.pharmacycounter.domain.response.register

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class RequestRegisterCustomer (
    @SerializedName("ten")
    val name:String,
    @SerializedName("id_agency")
    val idAgency : Int,
    @SerializedName("dia_chi")
    val address : String,
    @SerializedName("tinh")
    val provinces : Int,
    @SerializedName("sdt")
    val phone : String,
    val email : String ,
    val password: String,
    val img : MultipartBody.Part
)