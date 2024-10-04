package com.freshervnc.pharmacycounter.domain.response.bill

import com.google.gson.annotations.SerializedName

data class RequestBillResponse (
    @SerializedName("data_id")
    val dataId : List<Int>,
    val device : Int,
    @SerializedName("ten")
    val name : String,
    @SerializedName("sdt")
    val phone: String ,
    val email: String,
    @SerializedName("dia_chi")
    val address: String,
    @SerializedName("ma_so_thue")
    val fax : String,
    @SerializedName("ghi_chu")
    val note : String,
    @SerializedName("ck_truoc")
    val beforePayment : Int,
    val voucher : String ,
    val coin : Int,
    @SerializedName("total_price")
    val totalPrice : String
)