package com.example.ecommerce.authentication.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.authentication.domain.modules.LoginResponse
import com.example.ecommerce.authentication.domain.modules.RefreshTokenRequest
import com.example.ecommerce.authentication.domain.usecases.GetSavedRefreshTokenUseCase
import com.example.ecommerce.authentication.domain.usecases.GetUserDataUseCase
import com.example.ecommerce.authentication.domain.usecases.LoginUseCase
import com.example.ecommerce.authentication.domain.usecases.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val refreshToken: RefreshTokenUseCase,
    private val getUserData: GetUserDataUseCase,
    private val getSavedRefreshToken: GetSavedRefreshTokenUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _isAlreadyLogin = MutableStateFlow(false)
    val isAlreadyLogin: StateFlow<Boolean> get() = _isAlreadyLogin




    init {
        readRefreshTokenFromSharedPref()
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResponse: LoginResponse = login.invoke(loginRequest)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    private fun refreshToken(refreshTokenRequest: RefreshTokenRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
              val refreshTokenResponse = refreshToken.invoke(refreshTokenRequest)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    private fun readRefreshTokenFromSharedPref() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token: String? = getSavedRefreshToken.invoke()
                _isAlreadyLogin.value = (token != null)

                if (token != null){
                    refreshToken(
                        RefreshTokenRequest(token, 60)
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }
}