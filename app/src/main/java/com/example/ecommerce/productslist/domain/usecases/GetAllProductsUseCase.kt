package com.example.ecommerce.productslist.domain.usecases

import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.modules.ProductsResponse
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductsListRepository
) {
    suspend operator fun invoke(
        pageNumber: Int = 1,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): ProductsResponse {

        return repository.getAllProducts(
            pageNumber = pageNumber,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )
    }
}