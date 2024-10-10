package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class NewsRepository {
    suspend fun getNews() = RetrofitInstance.api.getNews()
}