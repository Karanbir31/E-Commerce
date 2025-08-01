package com.example.ecommerce.userDetails.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.ecommerce.helperUtils.SharedPrefKeys
import com.example.ecommerce.userDetails.domain.UserDetailsRepository
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import javax.inject.Inject

class UserDetailsRepositoryImp @Inject constructor(
    private val userDetailsApi: UserDetailsApi,
    private val sharedPreferences: SharedPreferences
) : UserDetailsRepository {

    override suspend fun getUserDetails(refreshToken: String): UserDetails {
        val response = userDetailsApi.getUserData(refreshToken)

        if (!response.isSuccessful) {
            throw Exception(
                "Failed to fetch user details. " +
                        "Error code: ${response.code()}, message: ${response.message()}"
            )
        }

        return response.body()
            ?: throw Exception("User details response body is null")
    }

    override suspend fun getUserAccessToken(): String {
        val token = sharedPreferences.getString(SharedPrefKeys.REFRESH_TOKEN, null)
        return token ?: throw Exception("User is not authenticated: access token not found")
    }


    override suspend fun saveAccessToken(accessToken: String) {
        try {
            sharedPreferences.edit {
                putString(SharedPrefKeys.REFRESH_TOKEN, accessToken)
            }
        }catch (e: Exception){
            throw Exception("Unable to store access token")
        }
    }


}