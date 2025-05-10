package com.example.ecommerce.authentication.domain

import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import com.example.ecommerce.authentication.domain.modules.RefreshTokenRequest
import com.example.ecommerce.authentication.domain.modules.RefreshTokenResponse

interface AuthRepository {

    suspend fun login(  loginRequest : LoginRequest) : LoginResponse

    suspend fun refreshToken( refreshTokenRequest: RefreshTokenRequest) : RefreshTokenResponse

    suspend fun getUserData( accessToken: String) : LoginResponse

    suspend fun getSavedRefreshToken() : String?

}