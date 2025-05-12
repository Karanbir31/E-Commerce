package com.example.ecommerce.userDetails.domain.usecases

import com.example.ecommerce.userDetails.domain.UserDetailsRepository
import javax.inject.Inject

class GetUserAccessTokenUseCase @Inject constructor(
    private val repository: UserDetailsRepository
) {
    suspend operator fun invoke() : String{
        return repository.getUserAccessToken()
    }

}