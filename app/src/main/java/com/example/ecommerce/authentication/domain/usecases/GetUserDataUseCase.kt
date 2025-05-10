package com.example.ecommerce.authentication.domain.usecases

import com.example.ecommerce.authentication.domain.AuthRepository
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(accessToken : String) : LoginResponse{
        return repository.getUserData(accessToken)
    }

}