package com.example.ecommerce.authentication.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.ecommerce.R
import com.example.ecommerce.authentication.domain.AuthRepository
import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import com.example.ecommerce.authentication.domain.modules.RefreshTokenRequest
import com.example.ecommerce.authentication.domain.modules.RefreshTokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit
import com.example.ecommerce.helperUtils.SharedPrefKeys

class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPref: SharedPreferences,
    @ApplicationContext private val context: Context
) : AuthRepository {

    private final val TAG = "AuthRepositoryImp"

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val response = authApi.login(loginRequest)
        if (response.isSuccessful) {

            response.body()?.let {body->
                saveRefreshToken(body.refreshToken)
                return body
            } ?: throw Exception("Login User response is null")

        } else {
            throw Exception("Login Failed Error \n Code : ${response.code()} ErrorMessage : ${response.message()}")
        }

    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        val response = authApi.refreshToken(refreshTokenRequest)
        if (response.isSuccessful) {

            response.body()?.let {body->
                saveRefreshToken(body.refreshToken)
                return body
            } ?: throw Exception("Response body is null for refreshToken fun")

        } else {
            throw Exception("refresh token Failed \n ErrorCode : ${response.code()} ErrorMessage : ${response.message()}")
        }
    }

    override suspend fun getUserData(accessToken: String): LoginResponse {
        val response = authApi.getUserData(accessToken)
        if (response.isSuccessful) {

            response.body()?.let {body->
                return body
            } ?: throw Exception("Response body is null for getUserData")

        } else {
            throw Exception("fail to read user data\n ErrorCode : ${response.code()} ErrorMessage : ${response.message()}")
        }
    }

    override suspend fun getSavedRefreshToken(): String? {
        try {
            return sharedPref.getString(SharedPrefKeys.REFRESH_TOKEN, null)
        } catch (e: Exception) {
            Log.e(TAG, "failed to read token from shared perf", e)
            return null
        }
    }

    // save refresh token into shared preference for further login request
    private fun saveRefreshToken(token: String) {
        try {
            sharedPref.edit {
                putString(SharedPrefKeys.REFRESH_TOKEN, token)
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed to save token into shared perf", e)
        }
    }

}
