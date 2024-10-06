package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.voucher.RequestVoucherResponse

class PaymentConfirmRepository {
//    suspend fun requestGetVoucher(authHeader : String , request : RequestVoucherResponse) = RetrofitInstance.api.getVoucher(authHeader,request)
    suspend fun requestGetVoucher(authHeader : String , request : List<Int>) = RetrofitInstance.api.getVoucher(authHeader,request)

}