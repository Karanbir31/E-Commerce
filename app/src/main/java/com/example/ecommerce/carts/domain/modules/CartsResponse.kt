package com.example.ecommerce.carts.domain.modules

data class CartsResponse(
    val carts: Cart,
    val total: Int,
    val skip: Int,
    val limit: Int
)
