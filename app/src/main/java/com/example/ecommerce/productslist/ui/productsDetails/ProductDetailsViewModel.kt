package com.example.ecommerce.productslist.ui.productsDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.usecases.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
): ViewModel() {

    private val TAG = "ProductDetailsViewModel"

    private val _productDetails = MutableStateFlow<Product>(Product())
    val productDetails : StateFlow<Product> get() = _productDetails

     fun getProductsDetails(productsId : Long){
         viewModelScope.launch(Dispatchers.IO){
             try {
                 _productDetails.value = getProductDetailsUseCase(productsId)
             }catch(e : Exception){
                 Log.e(TAG, e.message, e)
             }
         }
     }

}