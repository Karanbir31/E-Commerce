package com.example.ecommerce.carts.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.usecases.GetUsersCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CartsUiState {
    object Loading : CartsUiState()
    class Error(exception: Exception) : CartsUiState()
    class Success(data: Cart) : CartsUiState()
}

@HiltViewModel
class CartsViewModel @Inject constructor(
    private val getUsersCartUseCase: GetUsersCartUseCase
) : ViewModel() {

    private val TAG = "CartsViewModel"

    private val _usersCart = MutableStateFlow<CartsUiState>(CartsUiState.Loading)
    val usersCart: StateFlow<CartsUiState> get() = _usersCart

    fun getUsersCart(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _usersCart.value = CartsUiState.Success(getUsersCartUseCase(userId))
            } catch (e: Exception) {
                _usersCart.value = CartsUiState.Error(exception = e)
            }
        }
    }

}