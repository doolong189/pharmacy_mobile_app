package com.freshervnc.pharmacycounter.domain.response

import com.freshervnc.pharmacycounter.domain.models.Provinces

data class ProvincesResponse (val code : Int, val message : List<String> , val response : List<Provinces>)
