package com.example.ecommerce.carts.data

import com.example.ecommerce.carts.domain.CartsRepository
import com.example.ecommerce.carts.domain.modules.Cart
import javax.inject.Inject

class CartsRepositoryImp @Inject constructor(
    private val cartsApi: CartsApi
) : CartsRepository {

    override suspend fun getUsersCart(userId: Long): Cart {
        val response = cartsApi.getUsersCarts(userId)
        if (response.isSuccessful) {
            val body = response.body()
            return body ?: throw Exception("Response body is null for userId: $userId")
        } else {
            throw Exception("API call failed for userId: $userId. Code: ${response.code()}, Message: ${response.message()}")
        }
    }
}