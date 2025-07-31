package com.example.ecommerce.productslist.domain.usecases

import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.domain.modules.ProductsResponse
import javax.inject.Inject

class GetProductsByCategoriesUseCase @Inject constructor(
    private val repository: ProductsListRepository
) {
    suspend operator fun invoke(
        category: String,
        pageNumber: Int = 1,
        limit : Int = 25,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): ProductsResponse {

        return repository.getProductsByCategories(
            category = category,
            pageNumber = pageNumber,
            limit = limit ,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )
    }

}