package com.freshervnc.pharmacycounter.domain.models

import android.util.Printer
import com.google.gson.annotations.SerializedName

class Counter (
    private val id: Int,

    @SerializedName("ten")
    private val fullName: String,

    @SerializedName("ten_nha_thuoc")
    private val nameCounter: String,

    @SerializedName("sdt")
    private val phone : String,

    private val email: String ,

    @SerializedName("dia_chi")
    private val address: String,

    @SerializedName("ma_so_thue")
    private val taxCode : String,

    @SerializedName("trang_thai")
    private val status : Int,

    private val token : String,

    private val description : String
)