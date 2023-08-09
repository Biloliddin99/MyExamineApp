package com.bkm.myexamineapp.repository

import com.bkm.myexamineapp.network.ApiClient

object MyViewModelObjects {
    val myRepository = MyRepository(ApiClient.getRetrofitService())
}