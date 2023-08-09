package com.bkm.myexamineapp.network

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val PHOTO_BASE_URL = "https://rizonwebapp.pythonanywhere.com"
    const val BASE_URL="http://rizonwebapp.pythonanywhere.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitService():ApiService{
        return getRetrofit().create(ApiService::class.java)
    }
}