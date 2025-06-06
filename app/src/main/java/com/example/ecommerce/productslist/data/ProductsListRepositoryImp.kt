package com.example.ecommerce.productslist.data

import android.util.Log
import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.modules.ProductsResponse
import javax.inject.Inject

class ProductsListRepositoryImp @Inject constructor(
    private val productsApi: ProductsApi
) : ProductsListRepository {

    private val TAG = "ProductsListRepositoryImp"

    override suspend fun getAllProducts(
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): ProductsResponse {

        val response = productsApi.getAllProducts(
            limit = 15,
            skip = (pageNumber - 1) * 15,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )


        if (response.isSuccessful) {
            return response.body() ?: throw Exception("response body is null")
        } else {
            throw Exception("retrofit response is unsuccessful \n ErrorCode : ${response.code()} and ErrorMessage : ${response.message()}")
        }
    }

    override suspend fun getSearchedProducts(
        searchQuery: String,
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): ProductsResponse {
        val response = productsApi.getSearchedProducts(
            searchQuery = searchQuery,
            limit = 15,
            skip = (pageNumber - 1) * 15,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("response body is null")
        } else {
            throw Exception("retrofit response is unsuccessful\n ErrorCode : ${response.code()} and ErrorMessage : ${response.message()}")
        }

    }

    override suspend fun getProductsCategories(): List<String> {

        val response = productsApi.getProductsCategories()

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("retrofit response is unsuccessful\n ErrorCode : ${response.code()} and ErrorMessage : ${response.message()}")
        }

    }

    override suspend fun getProductsByCategories(
        category: String,
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): ProductsResponse {
        val response = productsApi.getProductsByCategories(
            category = category,
            limit = 15,
            skip = (pageNumber - 1) * 15,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("response body is null")
        } else {
            throw Exception("retrofit response is unsuccessful\n ErrorCode : ${response.code()} and ErrorMessage : ${response.message()}")
        }

    }

    override suspend fun getProductDetails(productId: Long): Product {
        val response = productsApi.getProductDetails(productId)
        if (response.isSuccessful) {

            response.body()?.let { body ->
                return body
            } ?: throw Exception("response body is null")

        } else {
            throw Exception("retrofit response is unsuccessful\n ErrorCode : ${response.code()} and ErrorMessage : ${response.message()}")
        }

    }


}