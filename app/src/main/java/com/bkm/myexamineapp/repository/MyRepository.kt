package com.bkm.myexamineapp.repository

import com.bkm.myexamineapp.network.ApiService


class MyRepository(val apiService: ApiService) {
    suspend fun getUserProducts(token: String) = apiService.getUserProducts("Bearer $token")
}