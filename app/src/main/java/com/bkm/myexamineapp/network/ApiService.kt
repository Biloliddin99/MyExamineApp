package com.bkm.myexamineapp.network

import com.bkm.myexamineapp.models.GetProductsResponse
import com.bkm.myexamineapp.models.MyToken
import com.bkm.myexamineapp.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("api/token/")
     fun getToken(@Body user: User):Call<MyToken>

    @GET("api/users-products/")
//    suspend fun getUserProducts(@Header("Authorization")token: String):Call<GetProductsResponse>
    suspend fun getUserProducts(@Header("Authorization")token: String):GetProductsResponse


}