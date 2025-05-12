package com.example.ecommerce.carts.domain.usecases

import com.example.ecommerce.carts.domain.CartsRepository
import com.example.ecommerce.carts.domain.modules.Cart
import javax.inject.Inject

class GetUsersCartUseCase @Inject constructor(
    private val repository: CartsRepository
) {

    suspend operator fun invoke(userId: Long): Cart {
        return repository.getUsersCart(userId = userId)
    }

}