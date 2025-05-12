package com.example.ecommerce.userDetails.data

import com.example.ecommerce.authentication.domain.modules.LoginResponse
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserDetailsApi {

    @GET("auth/me")
    suspend fun getUserData( @Header("Authorization") accessToken: String) : Response<UserDetails>

}