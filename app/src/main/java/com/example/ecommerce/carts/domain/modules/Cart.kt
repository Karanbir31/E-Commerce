package com.example.ecommerce.carts.domain.modules

import com.example.ecommerce.productslist.domain.modules.Product

data class Cart(
    val id : Long = 0,
    val products: List<CartsProducts> = emptyList(),
    val total: Float = 0f,
    val discountedTotal: Float = 0f,
    val userId: Long = 0,
    val totalProducts: Int = 0,
    val totalQuantity: Int = 0,
)

data class CartItem(
    val id : Long = 0,
    val products: List<Product> = emptyList(),
    val total: Float = 0f,
    val discountedTotal: Float = 0f,
    val userId: Long = 0,
    val totalProducts: Int = 0,
    val totalQuantity: Int = 0,
)


/*
{
  "carts": [
    {
      "id": 19,
      "products": [

        {...}
        // more products
      ],
      "total": 2492,
      "discountedTotal": 2140,
      "userId": 5, // user id is 5
      "totalProducts": 5,
      "totalQuantity": 14
    }
  ],
  "total": 1,
  "skip": 0,
  "limit": 1
}
 */