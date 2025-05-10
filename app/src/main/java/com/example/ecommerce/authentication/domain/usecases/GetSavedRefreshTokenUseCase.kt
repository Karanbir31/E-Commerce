package com.example.ecommerce.authentication.domain.usecases

import com.example.ecommerce.authentication.domain.AuthRepository
import javax.inject.Inject

class GetSavedRefreshTokenUseCase @Inject constructor (
    private val repository: AuthRepository
) {
    suspend operator fun invoke() : String?{
        return repository.getSavedRefreshToken()
    }

}