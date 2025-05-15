package com.example.ecommerce.carts.domain.modules

data class CartsResponse(
    val carts: Cart = Cart(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)
