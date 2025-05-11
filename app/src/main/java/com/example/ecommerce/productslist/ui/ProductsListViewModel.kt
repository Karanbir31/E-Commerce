package com.example.ecommerce.productslist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.productslist.domain.modules.Product
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

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

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
                _products.value = getAllProductsUseCase(pageNumber)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun searchProducts(searchQuery: String, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _products.value = getSearchedProductsUseCase.invoke(
                    searchQuery = searchQuery,
                    pageNumber = pageNumber
                )
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun searchProductsByCategory(category: String, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _products.value = getProductsByCategoriesUseCase.invoke(
                    category = category,
                    pageNumber = pageNumber
                )
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
        }
    }




}