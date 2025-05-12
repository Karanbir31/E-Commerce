package com.example.ecommerce.productslist.ui

import com.example.ecommerce.productslist.data.ProductsApi
import com.example.ecommerce.productslist.data.ProductsListRepositoryImp
import com.example.ecommerce.productslist.domain.ProductsListRepository
import com.example.ecommerce.productslist.domain.usecases.GetAllProductsUseCase
import com.example.ecommerce.productslist.domain.usecases.GetProductsByCategoriesUseCase
import com.example.ecommerce.productslist.domain.usecases.GetProductsCategoriesUseCase
import com.example.ecommerce.productslist.domain.usecases.GetSearchedProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class ProductsListRepositoryBinder {
    @Binds
    abstract fun bindRepositoryWithImp(
        productsListRepositoryImp: ProductsListRepositoryImp
    ): ProductsListRepository
}


@Module
@InstallIn(SingletonComponent::class)
interface ProductsListModule {

    @Provides
    @Singleton
    fun providesProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesGetAllProductsUseCase(
        repository: ProductsListRepository
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetProductsByCategoriesUseCase(
        repository: ProductsListRepository
    ): GetProductsByCategoriesUseCase {
        return GetProductsByCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetProductsCategoriesUseCase(
        repository: ProductsListRepository
    ): GetProductsCategoriesUseCase {
        return GetProductsCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetSearchedProductsUseCase(
        repository: ProductsListRepository
    ): GetSearchedProductsUseCase {
        return GetSearchedProductsUseCase(repository)
    }


}