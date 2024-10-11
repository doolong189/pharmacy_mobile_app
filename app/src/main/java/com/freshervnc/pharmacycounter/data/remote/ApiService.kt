package com.freshervnc.pharmacycounter.data.remote

import com.freshervnc.pharmacycounter.domain.response.AgencyResponse
import com.freshervnc.pharmacycounter.domain.response.login.LoginResponse
import com.freshervnc.pharmacycounter.domain.response.ProvincesResponse
import com.freshervnc.pharmacycounter.domain.response.bill.BillResponse
import com.freshervnc.pharmacycounter.domain.response.bill.DetailBillResponse
import com.freshervnc.pharmacycounter.domain.response.cart.CartResponse
import com.freshervnc.pharmacycounter.domain.response.cart.RequestCartResponse
import com.freshervnc.pharmacycounter.domain.response.homepage.HomePageResponse
import com.freshervnc.pharmacycounter.domain.response.register.RegisterResponse
import com.freshervnc.pharmacycounter.domain.response.login.RequestLoginResponse
import com.freshervnc.pharmacycounter.domain.response.bill.RequestBillResponse
import com.freshervnc.pharmacycounter.domain.response.bill.RequestDetailBillResponse
import com.freshervnc.pharmacycounter.domain.response.category.CategoryResponse
import com.freshervnc.pharmacycounter.domain.response.category.CategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.category.RequestCategoryTypeResponse
import com.freshervnc.pharmacycounter.domain.response.contact.ContactResponse
import com.freshervnc.pharmacycounter.domain.response.history.HistoryResponse
import com.freshervnc.pharmacycounter.domain.response.logout.LogoutResponse
import com.freshervnc.pharmacycounter.domain.response.news.NewsResponse
import com.freshervnc.pharmacycounter.domain.response.product.ProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestDeleteProductResponse
import com.freshervnc.pharmacycounter.domain.response.product.RequestProductResponse
import com.freshervnc.pharmacycounter.domain.response.profile.ProfileResponse
import com.freshervnc.pharmacycounter.domain.response.search.RequestSearchResponse
import com.freshervnc.pharmacycounter.domain.response.search.SearchResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestCreateCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestDeleteCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.storecategory.RequestUpdateCategoryResponse
import com.freshervnc.pharmacycounter.domain.response.voucher.RequestVoucherResponse
import com.freshervnc.pharmacycounter.domain.response.voucher.VoucherResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("api/v2/customer/login")
    suspend fun loginCustomer(@Body login : RequestLoginResponse) : LoginResponse
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

    //
    @GET("api/v2/customer/homepage")
    suspend fun getCustomerHomePage(@Header("Authorization") authHeader: String) :HomePageResponse

    /* Cart */
    @POST("api/v2/cart/update")
    suspend fun addProductToCart(@Header("Authorization") authHeader: String, @Body cart : RequestCartResponse) : CartResponse

    @POST("api/v2/cart/index")
    suspend fun getCart(@Header("Authorization") authHeader: String) : CartResponse

    /* Voucher */
    @GET("api/v2/cart/list_voucher")
    suspend fun getVoucher(@Header("Authorization") authHeader: String , @Query("data_id[]", encoded = true) dataId : List<Int>) : VoucherResponse
    /* Bill */
    @POST("api/v2/cart/payment")
    suspend fun createBill(@Header("Authorization") authHeader: String, @Body request : RequestBillResponse) : BillResponse

    @POST("api/v2/history/payment")
    suspend fun getHistoryBill(@Header("Authorization") authHeader: String , @Body page : Int) : HistoryResponse

    @POST("api/v2/history/payment_details")
    suspend fun getDetailHistoryBill(@Header("Authorization") authHeader: String, @Body request : RequestDetailBillResponse) : DetailBillResponse

    /* Category */
    @GET("/api/v2/system/category")
    suspend fun getCategory(@Header("Authorization") authHeader: String) : CategoryResponse

    @POST("api/v2/system/category_type")
    suspend fun getCategoryType(@Header("Authorization") authHeader: String , @Body request : RequestCategoryTypeResponse) : CategoryTypeResponse

    /* Product */
    @POST("/api/v2/product/index")
    suspend fun getProduct(@Header("Authorization") authHeader: String, @Body request : RequestProductResponse) : ProductResponse
    @POST("/api/v2/agency/products")
    suspend fun getStoreProduct(@Header("Authorization") authHeader: String, @Body request : RequestProductResponse) : ProductResponse

    @POST("api/v2/agency/product/delete")
    suspend fun deleteProduct(@Header("Authorization") authHeader: String, @Body request : RequestDeleteProductResponse) : RegisterResponse

    /* Logout */
    @POST("api/v2/member/logout")
    suspend fun logOut(@Header("Authorization") authHeader: String) : LogoutResponse

    /* Search */
    @POST("api/v2/search")
    suspend fun searchProduct(@Header("Authorization") authHeader: String, @Body request : RequestSearchResponse) : SearchResponse

    /* Store */
    @POST("api/v2/agency/category_type")
    suspend fun getStoreCategoryType(@Header("Authorization") authHeader: String , @Body request : RequestCategoryTypeResponse) : CategoryTypeResponse

    @GET("/api/v2/agency/category")
    suspend fun getStoreCategory(@Header("Authorization") authHeader: String) : CategoryResponse

    @GET("api/v2/member/profile")
    suspend fun getProfile(@Header("Authorization") authHeader: String) : ProfileResponse

    @POST("api/v2/agency/category_type/create")
    suspend fun getCreateStoreCategoryType(@Header("Authorization") authHeader: String , @Body request : RequestCreateCategoryResponse) : CategoryTypeResponse
    @POST("api/v2/agency/category_type/edit")
    suspend fun getUpdateStoreCategoryType(@Header("Authorization") authHeader: String , @Body request : RequestUpdateCategoryResponse) : CategoryTypeResponse
    @POST("api/v2/agency/category_type/delete")
    suspend fun getDeleteStoreCategoryType(@Header("Authorization") authHeader: String , @Body request : RequestDeleteCategoryResponse) : CategoryTypeResponse
    /* Contact */
    @GET("api/v2/system/contact")
    suspend fun getContact() : ContactResponse
    /* News */
    @GET("api/v2/system/list_news")
    suspend fun getNews() : NewsResponse
}
