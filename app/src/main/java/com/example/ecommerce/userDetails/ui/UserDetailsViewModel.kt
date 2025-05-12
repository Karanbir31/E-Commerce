package com.example.ecommerce.userDetails.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import com.example.ecommerce.userDetails.domain.usecases.GetUserAccessTokenUseCase
import com.example.ecommerce.userDetails.domain.usecases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getSavedRefreshTokenUseCase: GetUserAccessTokenUseCase
) : ViewModel() {
    private val TAG = "UserDetailsViewModel"

    private val _userDetails = MutableStateFlow<UserDetails>(UserDetails())
    val userDetails: StateFlow<UserDetails> get() = _userDetails

    private fun getUserDetails(refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userDetails.value = getUserDetailsUseCase(refreshToken)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    private fun getSavedRefreshToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val refreshToken = getSavedRefreshTokenUseCase()
                getUserDetails(refreshToken)
            } catch (e: Exception) {
                throw Exception("User is not authenticated", e)
            }
        }
    }
}