package com.example.ecommerce.carts.ui

import com.example.ecommerce.carts.data.CartsApi
import com.example.ecommerce.carts.data.CartsRepositoryImp
import com.example.ecommerce.carts.domain.CartsRepository
import com.example.ecommerce.carts.domain.usecases.GetUsersCartUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CartsRepositoryBinder {
    @Binds
    abstract fun repositoryBinder(cartsRepositoryImp: CartsRepositoryImp): CartsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CartsModules {

    @Provides
    @Singleton
    fun providerCartsApi(
        retrofit: Retrofit
    ): CartsApi {
        return retrofit.create(CartsApi::class.java)
    }


    @Provides
    @Singleton
    fun providerGetUsersCartUseCase(
        repository: CartsRepository
    ): GetUsersCartUseCase {
        return GetUsersCartUseCase(repository)
    }
}