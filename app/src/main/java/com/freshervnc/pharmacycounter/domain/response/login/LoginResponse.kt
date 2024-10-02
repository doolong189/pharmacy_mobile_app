package com.freshervnc.pharmacycounter.domain.response.login

import com.google.gson.annotations.SerializedName


data class LoginResponse (
    val code : Int,
    val message : List<String>,
    val response: Response
)

data class Response(
    private val id: Int,

    private val id_agency : Int, // customer

    @SerializedName("ten")
    private val fullName: String,

    @SerializedName("ten_nha_thuoc")
    private val nameCounter: String,

    @SerializedName("sdt")
    private val phone : String,

    private val email: String ,

    @SerializedName("dia_chi")
    private val address: String,

    @SerializedName("ma_so_thue")
    private val taxCode : String,

    @SerializedName("trang_thai")
    private val status : Int,

    private val agency : Int, // customer

    private val token : String,

    //
    private val description : String
)