package com.example.ecommerce.productslist.data

import com.example.ecommerce.productslist.domain.modules.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductsApi {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ) : Response<List<Product>>

    @GET("products/search")
    suspend fun getSearchedProducts(
        @Query("q") searchQuery: String,
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ): Response<List<Product>>

    @GET("products/category-list")
    suspend fun getProductsCategories(): Response<List<String>>

    @GET("products/{category}")
    suspend fun getProductsByCategories(
        @Path("category") category: String,
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ): Response<List<Product>>


}