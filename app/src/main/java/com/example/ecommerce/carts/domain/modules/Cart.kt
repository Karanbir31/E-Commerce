package com.example.ecommerce.carts.domain.modules

data class Cart(
    val id: Long,
    val products: List<CartsProducts>,
    val total: Float,
    val discountedTotal: Float,
    val userId: Long,
    val totalProducts: Int,
    val totalQuantity: Int
)
