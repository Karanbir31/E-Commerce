package com.example.ecommerce.productslist.domain.usecases

import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.modules.Product
import javax.inject.Inject

class GetProductsByCategoriesUseCase @Inject constructor(
    private val repository: ProductsListRepository
) {
    suspend operator fun invoke(
        category: String,
        pageNumber: Int = 1,
        sortBy: String = "title",
        sortingOrder: String = "asc"
    ): List<Product> {

        return repository.getProductsByCategories(
            category = category,
            pageNumber = pageNumber,
            sortBy = sortBy,
            sortingOrder = sortingOrder
        )
    }

}