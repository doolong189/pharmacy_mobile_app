package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse

class LoginRepository {

//    suspend fun requestLoginCustomer(login : RequestLoginResponse) = RetrofitInstance.api.loginCounter(login)

    suspend fun requestLoginCounter(login: RequestLoginResponse) = RetrofitInstance.api.loginCounter((login))
}