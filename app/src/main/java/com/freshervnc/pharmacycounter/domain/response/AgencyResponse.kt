package com.freshervnc.pharmacycounter.domain.response

import com.freshervnc.pharmacycounter.domain.models.Agency


data class AgencyResponse (
    val code: Int,
    val message : List<String>,
    val response : List<Agency>
){
    override fun toString(): String {
        return super.toString()
    }
}