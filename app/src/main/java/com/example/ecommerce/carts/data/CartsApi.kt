package com.example.ecommerce.carts.data

import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartsApi {

    @GET("carts/{userId}")
    suspend fun getUsersCarts(@Path("userId") userId: Long): Response<Cart>
}