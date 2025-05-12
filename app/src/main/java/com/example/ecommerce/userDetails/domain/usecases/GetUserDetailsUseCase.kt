package com.example.ecommerce.userDetails.domain.usecases

import com.example.ecommerce.userDetails.domain.UserDetailsRepository
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
  private val repository: UserDetailsRepository
) {

    suspend operator fun invoke(refreshToken : String) : UserDetails{
        return repository.getUserDetails(refreshToken)
    }

}