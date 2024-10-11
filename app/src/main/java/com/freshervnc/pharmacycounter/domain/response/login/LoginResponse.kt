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
     val fullName: String,

    @SerializedName("ten_nha_thuoc")
     val nameCounter: String,

    @SerializedName("sdt")
     val phone : String,

     val email: String ,

    @SerializedName("dia_chi")
     val address: String,

    @SerializedName("ma_so_thue")
    private val taxCode : String,

    @SerializedName("trang_thai")
    var status : Int,

    private val agency : Int, // customer

    val token : String,

    //
    val description : String
)