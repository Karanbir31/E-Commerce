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

class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPref: SharedPreferences,
    @ApplicationContext private val context: Context

) : AuthRepository {

    private final val TAG = "AuthRepositoryImp"

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val response = authApi.login(loginRequest)
            if (response.isSuccessful && response.body() != null) {

                saveRefreshToken(response.body()!!.refreshToken)

                return response.body()!!
            } else {
                throw Exception("Login Failed")
            }
        } catch (e: Exception) {
            throw Exception("Login Failed", e)
        }
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        try {
            val response = authApi.refreshToken(refreshTokenRequest)
            if (response.isSuccessful && response.body() != null) {

                saveRefreshToken(response.body()!!.refreshToken)

                return response.body()!!
            } else {
                throw Exception("refresh token Failed")
            }
        } catch (e: Exception) {
            throw Exception("refresh token Failed", e)
        }
    }

    override suspend fun getUserData(accessToken: String): LoginResponse {
        try {
            val response = authApi.getUserData(accessToken)
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                throw Exception("fail to read user data")
            }
        } catch (e: Exception) {
            throw Exception("fail to read user data", e)
        }
    }

    override suspend fun getSavedRefreshToken(): String? {
        try {
           return sharedPref.getString(context.getString(R.string.refresh_token), null)
        }catch (e: Exception){
            Log.e(TAG, "failed to read token from shared perf", e)
            return null
        }
    }

    // save refresh token into shared preference for further login request
    private fun saveRefreshToken(token: String) {
        try {
            sharedPref.edit {
                putString(context.getString(R.string.refresh_token), token)
            }
        } catch (e: Exception) {

        }
    }


}