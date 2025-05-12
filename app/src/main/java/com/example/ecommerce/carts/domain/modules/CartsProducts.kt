package com.example.ecommerce.carts.domain.modules

import android.health.connect.datatypes.units.Percentage

data class CartsProducts(
    val id: Long,
    val title: String,
    val price: Float,
    val quantity: Int,
    val discountPercentage: Float,
    val discountedTotal: Float,
    val thumbnail: String
)
