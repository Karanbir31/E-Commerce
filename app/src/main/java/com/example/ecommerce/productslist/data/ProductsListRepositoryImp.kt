package com.example.ecommerce.productslist.data

import android.util.Log
import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import javax.inject.Inject

class ProductsListRepositoryImp @Inject constructor(
    private val productsApi: ProductsApi
) : ProductsListRepository {

    private val TAG = "ProductsListRepositoryImp"

    override suspend fun getAllProducts(
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): List<Product> {
        try {
            val response = productsApi.getAllProducts(
                limit = 15,
                skip = (pageNumber - 1) * 15,
                sortBy = sortBy,
                sortingOrder = sortingOrder
            )

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("retrofit response is unsuccessful")
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            throw e
        }
    }

    override suspend fun getSearchedProducts(
        searchQuery: String,
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): List<Product> {
        try {
            val response = productsApi.getSearchedProducts(
                searchQuery = searchQuery,
                limit = 15,
                skip = (pageNumber - 1) * 15,
                sortBy = sortBy,
                sortingOrder = sortingOrder
            )

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("retrofit response is unsuccessful")
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            throw e
        }
    }

    override suspend fun getProductsCategories(): List<String> {
        try {
            val response = productsApi.getProductsCategories()

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("retrofit response is unsuccessful")
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            throw e
        }
    }

    override suspend fun getProductsByCategories(
        category: String,
        pageNumber: Int,
        sortBy: String,
        sortingOrder: String
    ): List<Product> {
        try {
            val response = productsApi.getProductsByCategories(
                category = category,
                limit = 15,
                skip = (pageNumber - 1) * 15,
                sortBy = sortBy,
                sortingOrder = sortingOrder
            )

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("retrofit response is unsuccessful")
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            throw e
        }
    }


}