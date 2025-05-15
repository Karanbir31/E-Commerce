package com.example.ecommerce.productslist.data

import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.modules.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductsApi {
    @GET("products")
    suspend fun getAllProducts(
    ) : Response<ProductsResponse>

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ) : Response<ProductsResponse>

    @GET("products/search")
    suspend fun getSearchedProducts(
        @Query("q") searchQuery: String,
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ): Response<ProductsResponse>

    @GET("products/category-list")
    suspend fun getProductsCategories(): Response<List<String>>

    //'https://dummyjson.com/products/category/smartphones'
    @GET("products/category/{category}")
    suspend fun getProductsByCategories(
        @Path("category") category: String,
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") sortingOrder : String
    ): Response<ProductsResponse>


    @GET("products/{productId}")
    suspend fun getProductDetails(
       @Path("productId") productId: Long
    ) : Response<Product>

}