package com.example.ecommerce.productslist.domain.usecases

import com.example.ecommerce.productslist.domain.ProductsListRepository
import javax.inject.Inject

class getProductsCategoriesUseCase @Inject constructor(
    private val repository: ProductsListRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getProductsCategories()
    }

}