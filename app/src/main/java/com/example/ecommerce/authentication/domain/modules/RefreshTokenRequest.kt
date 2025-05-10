package com.example.ecommerce.authentication.domain.modules

data class RefreshTokenRequest(
    val refreshToken : String = "",
    val expiresInMins : Int = 60
)
