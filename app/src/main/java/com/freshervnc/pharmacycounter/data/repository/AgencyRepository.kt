package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance

class AgencyRepository
{
    suspend fun getAgency(id : Int) = RetrofitInstance.api.getAddressCounter(id)
}