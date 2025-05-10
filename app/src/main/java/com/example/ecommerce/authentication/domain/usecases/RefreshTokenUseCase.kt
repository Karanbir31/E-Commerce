package com.example.ecommerce.authentication.domain.usecases

import com.example.ecommerce.authentication.domain.AuthRepository
import com.example.ecommerce.authentication.domain.modules.RefreshTokenRequest
import com.example.ecommerce.authentication.domain.modules.RefreshTokenResponse
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(refreshTokenRequest: RefreshTokenRequest) : RefreshTokenResponse{
        return repository.refreshToken(refreshTokenRequest)
    }

}