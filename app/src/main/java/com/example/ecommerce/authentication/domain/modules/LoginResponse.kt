package com.example.ecommerce.authentication.domain.modules

data class LoginResponse(
    val id : Long = 0,
    val username: String = "",
    val email : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val gender : String = "",
    val image : String = "",
    val accessToken : String = "",
    val refreshToken : String = ""
)

