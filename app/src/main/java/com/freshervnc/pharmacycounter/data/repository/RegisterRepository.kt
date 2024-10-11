package com.freshervnc.pharmacycounter.data.repository

import com.freshervnc.pharmacycounter.data.remote.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterRepository {

//    suspend fun requestRegisterCounter(response: RequestRegisterCounter) = RetrofitInstance.api.registerCounter(response)

    suspend fun requestRegisterCounter(fullNameBody: RequestBody,
                                       nameCounter: RequestBody,
                                       address: RequestBody ,
                                       provinces: RequestBody,
                                       phone: RequestBody ,
                                       email: RequestBody,
                                       password: RequestBody,
                                       img : MultipartBody.Part
    ) = RetrofitInstance.api.registerCounter(fullNameBody,nameCounter,address,provinces,phone,email,password,img)

//    suspend fun requestRegisterCustomer(response: RequestRegisterCustomer) = RetrofitInstance.api.registerCustomer(response)

    suspend fun requestRegisterCustomer(fullNameBody: RequestBody, idAgency: RequestBody,
                                       address: RequestBody ,
                                       provinces: RequestBody,
                                       phone: RequestBody ,
                                       email: RequestBody,
                                       password: RequestBody,
                                       img : MultipartBody.Part
    ) = RetrofitInstance.api.registerCustomer(fullNameBody,idAgency,address,provinces,phone,email,password,img)
}