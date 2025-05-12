package com.example.ecommerce.userDetails.ui

import com.example.ecommerce.userDetails.data.UserDetailsApi
import com.example.ecommerce.userDetails.data.UserDetailsRepositoryImp
import com.example.ecommerce.userDetails.domain.UserDetailsRepository
import com.example.ecommerce.userDetails.domain.usecases.GetUserAccessTokenUseCase
import com.example.ecommerce.userDetails.domain.usecases.GetUserDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDetailsRepositoryBinder{

    @Binds
    abstract fun bindRepository(userDetailsRepositoryImp: UserDetailsRepositoryImp) : UserDetailsRepository
}


@Module
@InstallIn(SingletonComponent::class)
object UserDetailsModule {

    @Provides
    @Singleton
    fun provideUserDetailsApi(retrofit: Retrofit) : UserDetailsApi{
       return retrofit.create(UserDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetUserAccessTokenUseCase(
        repository: UserDetailsRepository
    ) : GetUserAccessTokenUseCase{
       return GetUserAccessTokenUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserDetailsUseCase(
        repository: UserDetailsRepository
    ) : GetUserDetailsUseCase{
        return GetUserDetailsUseCase(repository)
    }


}