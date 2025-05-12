package com.example.ecommerce.carts.domain

import com.example.ecommerce.carts.domain.modules.Cart

interface CartsRepository {

    suspend fun getUsersCart(userId : Long) : Cart


}