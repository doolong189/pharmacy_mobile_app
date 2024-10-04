package com.freshervnc.pharmacycounter.domain.response.voucher


data class VoucherResponse (
    val code : Int,
    val message : List<String>,
    val response : List<Response>
)

data class Response (
    val id: Int,
    val title: String,
    val value : String,
    val content : String
)