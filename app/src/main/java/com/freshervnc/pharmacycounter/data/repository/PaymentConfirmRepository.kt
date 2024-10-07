package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import com.freshervnc.pharmacycounter.domain.response.voucher.RequestVoucherResponse

class PaymentConfirmRepository {
    suspend fun getVoucher(authHeader : String , dataId : List<Int>) = RetrofitInstance.api.getVoucher(authHeader,dataId)

}