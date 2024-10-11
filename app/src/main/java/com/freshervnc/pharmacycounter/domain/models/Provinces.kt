package com.freshervnc.pharmacycounter.domain.models

import com.google.gson.annotations.SerializedName

data class Provinces (
    var id: Int,
    @SerializedName("ten")
    var name : String,
    @SerializedName("sdt")
    private val phone : String,
    @SerializedName("dia_chi")
    private val address : String
){
    override fun toString(): String {
        return name
    }
}