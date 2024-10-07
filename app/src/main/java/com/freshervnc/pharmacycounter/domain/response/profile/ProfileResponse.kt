package com.freshervnc.pharmacycounter.domain.response.profile

import com.freshervnc.pharmacycounter.domain.models.Provinces

data class ProfileResponse (
    val code : Int,
    var message : List<String>,
    var response : Response
)

data class Response(
    var ten : String,
    var tenNhaThuoc : String,
    var diaChi : String,
    val email : String,
    var sdt : String,
    var tinh : Int ,
    var maSoThue : String,
    var img :  String,
    var trangThai : Int,
    var provinces : List<Provinces>
)