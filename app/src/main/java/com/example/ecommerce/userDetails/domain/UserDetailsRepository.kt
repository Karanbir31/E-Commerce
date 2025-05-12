package com.example.ecommerce.userDetails.domain

import com.example.ecommerce.userDetails.domain.modules.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(refreshToken : String) : UserDetails

    suspend fun getUserAccessToken() : String

}