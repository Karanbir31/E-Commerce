package com.example.ecommerce.authentication.domain.usecases

import com.example.ecommerce.authentication.domain.AuthRepository
import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest) : LoginResponse{
        return repository.login(loginRequest)
    }
}