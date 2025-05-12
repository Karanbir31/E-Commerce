package com.example.ecommerce.productslist.domain.usecases

import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
   private val repository: ProductsListRepository
) {
    suspend operator fun invoke(productId : Long) : Product{
        return repository.getProductDetails(productId)
    }
}