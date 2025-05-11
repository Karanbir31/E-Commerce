package com.example.ecommerce.productslist.domain.modules

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val price: Float,
    val discountPercentage: Float,
    val rating: Float,
    val stock: Long,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val dimensions: Dimensions,
    val warrantInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>
)
