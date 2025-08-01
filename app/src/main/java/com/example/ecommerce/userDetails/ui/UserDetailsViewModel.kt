package com.example.ecommerce.userDetails.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import com.example.ecommerce.userDetails.domain.usecases.GetUserAccessTokenUseCase
import com.example.ecommerce.userDetails.domain.usecases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserDetailsUiState {
    object Loading : UserDetailsUiState()
    class Error(val exception: Exception) : UserDetailsUiState()
    class Success(val data: UserDetails) : UserDetailsUiState()
}

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getSavedRefreshTokenUseCase: GetUserAccessTokenUseCase
) : ViewModel() {

    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3NTQwNDI2MzYsImV4cCI6MTc1NjYzNDYzNn0.kXLlSuuOsFa995_SljSM_3JcnDiVRI1xAaVh12RYMwc"

    private val TAG = "UserDetailsViewModel"

    private val _userDetails = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val userDetails: StateFlow<UserDetailsUiState> get() = _userDetails

    private val _navigateAuthScreen = MutableSharedFlow<Unit>()
    val navigateAuthScreen = _navigateAuthScreen.asSharedFlow()


    init {
        getUserTokenFromSharedPref()
    }

    private fun getUserDetails(refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _userDetails.value = UserDetailsUiState.Success(getUserDetailsUseCase(refreshToken))
            } catch (e: Exception) {
                _userDetails.value = UserDetailsUiState.Error(exception = e)
            }
        }
    }

    private fun getUserTokenFromSharedPref() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "getUserTokenFromSharedPref: call get user toke")

            try {
                val refreshToken = getSavedRefreshTokenUseCase()
                Log.d(TAG, "getUserTokenFromSharedPref: token - $refreshToken")

                getUserDetails(refreshToken)
            } catch (e: Exception) {
                Log.d(TAG, "getUserTokenFromSharedPref: catch")

                triggerNavigation()
            }
        }
    }

    private fun triggerNavigation() {
        viewModelScope.launch {
            _navigateAuthScreen.emit(Unit)
        }
    }
}