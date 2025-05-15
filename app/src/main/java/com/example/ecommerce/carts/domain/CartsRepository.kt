package com.example.ecommerce.carts.domain

import com.example.ecommerce.carts.domain.modules.CartsResponse

interface CartsRepository {

    suspend fun getUsersCart() : CartsResponse


}