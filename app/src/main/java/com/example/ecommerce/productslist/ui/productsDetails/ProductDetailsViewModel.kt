package com.example.ecommerce.productslist.ui.productsDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.usecases.GetProductDetailsUseCase
import com.example.ecommerce.productslist.domain.usecases.GetProductsByCategoriesUseCase
import com.example.ecommerce.productslist.ui.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ProductDetailsUiState() {
    data object Loading : ProductDetailsUiState()
    class Error(val exception: Exception) : ProductDetailsUiState()
    class Success(val data: Product) : ProductDetailsUiState()
}


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase
) : ViewModel() {

    private val TAG = "ProductDetailsViewModel"

    private val _productDetails =
        MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val productDetails: StateFlow<ProductDetailsUiState> get() = _productDetails


    private val _similarProducts =
        MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val similarProducts: StateFlow<ProductsUiState> get() = _similarProducts


    fun getProductsDetails(productsId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val product = getProductDetailsUseCase(productsId)

                _productDetails.value = ProductDetailsUiState.Success(product)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }


    fun searchProductsByCategory(category: String, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getProductsByCategoriesUseCase.invoke(
                    category = category,
                    pageNumber = pageNumber,
                    limit = 5
                )
                _similarProducts.value = ProductsUiState.Success(data)
            } catch (e: Exception) {
                _similarProducts.value = ProductsUiState.Error(e)
            }
        }
    }

}