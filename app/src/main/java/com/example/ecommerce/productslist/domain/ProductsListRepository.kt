package com.example.ecommerce.productslist.domain

import com.example.ecommerce.productslist.domain.modules.Product

interface ProductsListRepository {

    suspend fun getProducts(
        pageNumber: Int = 1,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): List<Product>

    suspend fun getSearchedProducts(
        searchQuery: String,
        pageNumber: Int = 1,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): List<Product>

    suspend fun getProductsCategories(): List<String>

    suspend fun getProductsByCategories(
        category: String,
        pageNumber: Int = 1,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): List<Product>


}
