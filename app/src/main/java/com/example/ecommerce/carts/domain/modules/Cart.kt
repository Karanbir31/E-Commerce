package com.example.ecommerce.carts.domain.modules

data class Cart(
    val id: Long  = 0,
    val products: List<CartsProducts> = emptyList(),
    val total: Float = 0f,
    val discountedTotal: Float = 0f,
    val userId: Long = 0,
    val totalProducts: Int = 0,
    val totalQuantity: Int = 0
)

