package com.example.ecommerce.carts.domain.modules

import android.health.connect.datatypes.units.Percentage

data class CartsProducts(
    val id: Long,
    val title: String,
    val price: Float,
    val quantity: Int,
    val total: Float,
    val discountPercentage: Float,
    val discountedTotal: Float,
    val thumbnail: String
)

/*
     {
          "id": 144,
          "title": "Cricket Helmet",
          "price": 44.99,
          "quantity": 4,
          "total": 179.96,
          "discountPercentage": 11.47,
          "discountedTotal": 159.32,
          "thumbnail": "https://cdn.dummyjson.com/products/images/sports-accessories/Cricket%20Helmet/thumbnail.png"
        },
 */