package com.example.ecommerce.authentication.data

import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import com.example.ecommerce.authentication.domain.modules.RefreshTokenRequest
import com.example.ecommerce.authentication.domain.modules.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login( @Body loginRequest : LoginRequest) : Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refreshToken( @Body refreshTokenRequest: RefreshTokenRequest) : Response<RefreshTokenResponse>

    @GET("auth/me")
    suspend fun getUserData( @Header("Authorization") accessToken: String) : Response<LoginResponse>

}
