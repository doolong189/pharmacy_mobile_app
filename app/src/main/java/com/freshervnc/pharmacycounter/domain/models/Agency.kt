package com.freshervnc.pharmacycounter.domain.models

import com.google.gson.annotations.SerializedName

data class Agency (
    private val id: Int,
    @SerializedName("ten_nha_thuoc")
    private val name : String,
    @SerializedName("sdt")
    private val phone : String,
    @SerializedName("dia_chi")
    private val address : String
){
    override fun toString(): String {
        return name
    }
}