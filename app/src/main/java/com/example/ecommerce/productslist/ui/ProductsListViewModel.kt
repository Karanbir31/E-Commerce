package com.example.ecommerce.productslist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.modules.ProductsResponse
import com.example.ecommerce.productslist.domain.usecases.GetAllProductsUseCase
import com.example.ecommerce.productslist.domain.usecases.GetProductsByCategoriesUseCase
import com.example.ecommerce.productslist.domain.usecases.GetProductsCategoriesUseCase
import com.example.ecommerce.productslist.domain.usecases.GetSearchedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductsUiState() {
    object Loading : ProductsUiState()
    class Error(val exception: Exception) : ProductsUiState()
    class Success(val data: ProductsResponse) : ProductsUiState()
}


@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val getProductsCategoriesUseCase: GetProductsCategoriesUseCase
) : ViewModel() {
    private final val TAG = "ProductsListViewModel"

    private val _productCategories = MutableStateFlow<List<String>>(emptyList())
    val productCategories: StateFlow<List<String>> get() = _productCategories

    private val _productsUiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val productsUiState: StateFlow<ProductsUiState> get() = _productsUiState


    init {
        getAllProducts(pageNumber = 1)

        getProductCategories()
    }

    private fun getProductCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productCategories.value = getProductsCategoriesUseCase.invoke()
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun getAllProducts(pageNumber: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getAllProductsUseCase(pageNumber)
                _productsUiState.value = ProductsUiState.Success(data)
            } catch (e: Exception) {
                _productsUiState.value = ProductsUiState.Error(e)
            }
        }
    }

    fun searchProducts(searchQuery: String, pageNumber: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
               val data = getSearchedProductsUseCase.invoke(
                    searchQuery = searchQuery,
                    pageNumber = pageNumber
                )
                _productsUiState.value = ProductsUiState.Success(data)
            } catch (e: Exception) {
                _productsUiState.value = ProductsUiState.Error(e)
            }
        }
    }

    fun searchProductsByCategory(category: String, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getProductsByCategoriesUseCase.invoke(
                    category = category,
                    pageNumber = pageNumber
                )
                _productsUiState.value = ProductsUiState.Success(data)
            } catch (e: Exception) {
                _productsUiState.value = ProductsUiState.Error(e)
            }
        }
    }

}