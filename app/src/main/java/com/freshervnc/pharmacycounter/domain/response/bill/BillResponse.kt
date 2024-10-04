package com.freshervnc.pharmacycounter.domain.response.bill

import com.google.gson.annotations.SerializedName


data class BillResponse (
    val code : Int,
    val message: List<String>,
    val response : Response
)

data class Response(
    val description : String,
    @SerializedName("url_payment")
    val urlPayment: String
)