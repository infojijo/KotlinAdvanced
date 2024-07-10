package com.example.myapp1.di

import com.example.myapp1.models.ListRepositoryImpl
import com.example.myapp1.network.MyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ListModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: ListRepositoryImpl
    ): MyRepository
}
