package com.freshervnc.pharmacycounter.domain.response.register


data class RegisterResponse (
    val code: Int,
    val message : List<String>,
    val response : Response
)

data class Response(var description : String)