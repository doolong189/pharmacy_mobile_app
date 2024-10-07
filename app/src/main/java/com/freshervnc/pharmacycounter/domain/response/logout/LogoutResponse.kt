package com.freshervnc.pharmacycounter.domain.response.logout

import com.freshervnc.pharmacycounter.domain.response.login.Response

data class LogoutResponse(
    val code : Int,
    val message : List<String>,
    val response : Response
)