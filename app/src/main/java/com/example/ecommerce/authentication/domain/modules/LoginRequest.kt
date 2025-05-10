package com.example.ecommerce.authentication.domain.modules

data class LoginRequest(
    val username : String = "",
    val password : String = "",
    val expiresInMins : Int = 60
)
