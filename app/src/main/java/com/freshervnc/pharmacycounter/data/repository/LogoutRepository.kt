package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class LogoutRepository {

    suspend fun requestLogOut(authHeader : String ) = RetrofitInstance.api.logOut(authHeader)
}