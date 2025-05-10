package com.example.ecommerce.authentication.ui

import com.example.ecommerce.authentication.data.AuthApi
import com.example.ecommerce.authentication.data.AuthRepositoryImp
import com.example.ecommerce.authentication.domain.AuthRepository
import com.example.ecommerce.authentication.domain.usecases.GetSavedRefreshTokenUseCase
import com.example.ecommerce.authentication.domain.usecases.GetUserDataUseCase
import com.example.ecommerce.authentication.domain.usecases.LoginUseCase
import com.example.ecommerce.authentication.domain.usecases.RefreshTokenUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindAuthRepository{
    @Binds
    abstract fun bindAuthRepository( authRepositoryImp: AuthRepositoryImp) : AuthRepository

}


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(repository: AuthRepository): RefreshTokenUseCase {
        return RefreshTokenUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserDataUseCase(repository: AuthRepository): GetUserDataUseCase {
        return GetUserDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedRefreshTokenUseCase(repository: AuthRepository): GetSavedRefreshTokenUseCase {
        return GetSavedRefreshTokenUseCase(repository)
    }


}