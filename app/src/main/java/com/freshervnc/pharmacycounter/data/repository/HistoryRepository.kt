package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse

class HistoryRepository {
    suspend fun getHistory(authHeader : String , page : Int) = RetrofitInstance.api.getHistoryBill(authHeader,page)
    suspend fun detailHistory(authHeader: String , request : RequestDetailBillResponse) = RetrofitInstance.api.getDetailHistoryBill(authHeader,request)

}