package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class ProfileRepository {
    suspend fun getProfile(authHeader : String) = RetrofitInstance.api.getProfile(authHeader)
}