package com.example.ecommerce.authentication.domain.modules

data class RefreshTokenResponse(
    val accessToken : String = "",
    val refreshToken : String = ""
)
