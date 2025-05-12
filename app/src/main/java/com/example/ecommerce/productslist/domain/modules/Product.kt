package com.example.ecommerce.productslist.domain.modules

data class Product(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Float = 0f,
    val discountPercentage: Float = 0f,
    val rating: Float = 0f,
    val stock: Long = 0,
    val tags: List<String> = emptyList(),
    val brand: String = "",
    val sku: String = "",
    val dimensions: Dimensions = Dimensions(),
    val warrantInformation: String = "",
    val shippingInformation: String = "",
    val availabilityStatus: String = "",
    val reviews: List<Review> = emptyList()
)
