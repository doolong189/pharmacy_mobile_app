package com.freshervnc.pharmacycounter.domain.response.contact

data class ContactResponse (
    val code : Int,
    val message : List<String>,
    val response : List<Response>
)

data class Response(
    val icon : String,
    val name : String,
    val value : String,
    val type : Int
)