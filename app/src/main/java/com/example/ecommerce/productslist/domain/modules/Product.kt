package com.example.ecommerce.productslist.domain.modules

import coil3.Image

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
    val reviews: List<Review> = emptyList(),
    val returnPolicy: String = "",
    val minimumOrderQuantity: Int = 0,
    val meta: ProductMetaData = ProductMetaData(),
    val images: List<String> = emptyList(),
    val thumbnail: String = ""
)

data class ProductMetaData(
    val createdAt: String = "",
    val updatedAt: String = "",
    val barcode: String = "",
    val qrCode: String = ""
)