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

    override suspend fun getUserDetails(accessToken: String): UserDetails {
        val userDetailsResponse = userDetailsApi.getUserData(accessToken)
        if (userDetailsResponse.isSuccessful) {
            userDetailsResponse.body()?.let { body ->
                return body
            } ?: throw Exception("Users details response's body from api is null")
        } else {
            throw Exception("Api call for user's details is failed\n ErrorMessage " +
                    "${userDetailsResponse.message()} ErrorCode ${userDetailsResponse.code()}")
        }
    }

    override suspend fun getUserAccessToken(): String {
        val accessToken = sharedPreferences.getString(SharedPrefKeys.ACCESS_TOKEN, null)
        accessToken?.let { it ->
            return it
        } ?: throw Exception("User is not authenticated ")
    }

    override suspend fun saveAccessToken(accessToken: String) {
        try {
            sharedPreferences.edit {
                putString(SharedPrefKeys.ACCESS_TOKEN, accessToken)
            }
        }catch (e: Exception){
            throw Exception("Unable to store access token")
        }
    }


}