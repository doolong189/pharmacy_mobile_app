package com.freshervnc.pharmacycounter.data.remote

import com.freshervnc.pharmacycounter.domain.response.AgencyResponse
import com.freshervnc.pharmacycounter.domain.response.login.LoginResponse
import com.freshervnc.pharmacycounter.domain.response.ProvincesResponse
import com.freshervnc.pharmacycounter.domain.response.homepage.HomePageResponse
import com.freshervnc.pharmacycounter.domain.response.register.RegisterResponse
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    /* provinces */
    @GET("api/v2/system/provinces")
    suspend fun getProvinces() : ProvincesResponse

    /* member */
    @Multipart
    @POST("api/v2/member/register")
    suspend fun registerCounter(
        @Part("ten") fullName: RequestBody,
        @Part("ten_nha_thuoc") nameCounter: RequestBody,
        @Part("dia_chi") address: RequestBody,
        @Part("tinh") provinces: RequestBody,
        @Part("sdt") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part img: MultipartBody.Part
    ) : RegisterResponse

    @POST("api/v2/member/login")
    suspend fun loginCounter(@Body login : RequestLoginResponse) : LoginResponse
    /* customer */

    @Multipart
    @POST("api/v2/customer/register")
    suspend fun registerCustomer(
        @Part("ten") fullName: RequestBody,
        @Part("id_agency") idAgency: RequestBody,
        @Part("dia_chi") address: RequestBody,
        @Part("tinh") provinces: RequestBody,
        @Part("sdt") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part img: MultipartBody.Part
    ) : RegisterResponse

    @GET("api/v2/system/provinces/agency_list/")
    suspend fun getAddressCounter(@Query("province_id") id : Int) : AgencyResponse

    /* Home */
    @GET("api/v2/system/homepage")
    suspend fun getHomePage(@Header("Authorization") authHeader: String) : HomePageResponse
}