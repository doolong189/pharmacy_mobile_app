package com.freshervnc.pharmacycounter.domain.models

import com.google.gson.annotations.SerializedName

data class Provinces (
    private val id: Int,
    @SerializedName("ten")
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