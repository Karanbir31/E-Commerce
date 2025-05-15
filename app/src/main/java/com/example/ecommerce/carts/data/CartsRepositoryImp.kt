package com.example.ecommerce.carts.data

import android.content.SharedPreferences
import com.example.ecommerce.carts.domain.CartsRepository
import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartsResponse
import com.example.ecommerce.helperUtils.SharedPrefKeys
import javax.inject.Inject

class CartsRepositoryImp @Inject constructor(
    private val cartsApi: CartsApi,
    private val sharedPref: SharedPreferences
) : CartsRepository {

    override suspend fun getUsersCart(): CartsResponse {

        val userAccessToken = sharedPref.getString(SharedPrefKeys.ACCESS_TOKEN, null)

        if (userAccessToken == null){
            throw Exception("Unknown user, login first")
        }

        val response = cartsApi.getUsersCarts(userAccessToken.toLong())
        if (response.isSuccessful) {
            val body = response.body()
            return body ?: throw Exception("Response body is null")
        } else {
            throw Exception("API call failed for userId: $userAccessToken. Code: ${response.code()}, Message: ${response.message()}")
        }
    }
}