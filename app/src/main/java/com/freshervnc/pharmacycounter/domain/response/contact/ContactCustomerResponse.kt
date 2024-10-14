package com.freshervnc.pharmacycounter.domain.response.contact

import com.google.gson.annotations.SerializedName

data class ContactCustomerResponse (
    val code : String,
    val message : List<String>,
    val response : ResponseCustomer
)

data class ResponseCustomer(
    val contact : List<Response>,
    val info : Info
)

data class Info(
    @SerializedName("ten_nha_thuoc")
    val tenNhaThuoc: String,
    val email : String,
    @SerializedName("dia_chi")
    val diaChi : String,
    val tinh : String
)