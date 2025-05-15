package com.example.ecommerce.carts.domain

import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartsResponse

interface CartsRepository {

    suspend fun getUsersCart() : Cart


}