package com.bkm.myexamineapp.models

data class GetProductsResponse(
    val discounts: List<Discount>,
    val follower_link: String,
    val products: List<Product>,
    val sale_link: String
)