package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse

class HistoryRepository {
    suspend fun getHistory(authHeader : String , page : Int) = RetrofitInstance.api.getHistoryBill(authHeader,page)
    suspend fun getCustomerHistory(authHeader : String , page : Int) = RetrofitInstance.api.getHistoryBill(authHeader,page)
    suspend fun detailHistory(authHeader: String , request : RequestDetailBillResponse) = RetrofitInstance.api.getDetailHistoryBill(authHeader,request)
    suspend fun detailCustomerHistory(authHeader: String , request : RequestDetailBillResponse) = RetrofitInstance.api.getDetailHistoryBill(authHeader,request)

    //14-10-2024
    suspend fun getHistorySellAgency(authHeader : String , page : Int) = RetrofitInstance.api.getHistorySellAgency(authHeader,page)
    suspend fun detailAgencyHistory(authHeader: String , request : RequestDetailBillResponse) = RetrofitInstance.api.getHistoryDetailAgency(authHeader,request)
}