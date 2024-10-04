package com.freshervnc.pharmacycounter.domain.response.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse (
    val code : Int,
    val message: List<String>,
    val response : Response,
    @SerializedName("total_product")
    val totalProduct: Int,
    @SerializedName("total_price")
    val totalPrice : Int
)

data class Response(
    val currentPage : Int,
    val data : List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl : String,
    val from : Int,
    @SerializedName("last_page")
    val lastPage : Int,
    @SerializedName("last_page_url")
    val lastPageUrl : String,
    @SerializedName("next_page_url")
    val nextPageUrl : Int,
    val path : String,
    @SerializedName("per_page")
    val perPage:Int,
    @SerializedName("perv_page_url")
    val pervPageUrl : Int,
    val to: Int,
    val total : Int
)

data class Data(
    val id: Int,
    @SerializedName("ma_don_hang")
    val maDonHang : String,
    @SerializedName("dia_chi")
    val address: String,
    @SerializedName("created_at")
    val createAt : String,
    @SerializedName("ck_truoc")
    val prePayment : Int,
    @SerializedName("ti_le_giam")
    val percentReduce : Int,
    @SerializedName("trang_thai")
    val status : Int,
    val voucher : String,
    @SerializedName("voucher_value")
    val voucherValue : Int,
    val coins : Int,
    @SerializedName("coin_value")
    val coinValue : Int,
    @SerializedName("ghi_chu")
    val note : String,
    @SerializedName("tong_tien")
    val totalAmount : Int
)