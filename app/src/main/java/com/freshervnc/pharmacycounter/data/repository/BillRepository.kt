package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.bill.RequestBillResponse
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse

class BillRepository {
    suspend fun createBill(authHeader : String , request : RequestBillResponse) = RetrofitInstance.api.createBill(authHeader,request)
}