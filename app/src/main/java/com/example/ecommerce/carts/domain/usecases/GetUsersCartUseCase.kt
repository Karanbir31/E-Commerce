package com.example.ecommerce.carts.domain.usecases

import com.example.ecommerce.carts.domain.CartsRepository
import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartsResponse
import javax.inject.Inject

class GetUsersCartUseCase @Inject constructor(
    private val repository: CartsRepository
) {

    suspend operator fun invoke(): Cart {
        return repository.getUsersCart()
    }

}