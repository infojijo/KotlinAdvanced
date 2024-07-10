package com.example.myapp1.di

import com.example.myapp1.repository.CommentsRepositoryImpl
import com.example.myapp1.repository.CommentsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: CommentsRepositoryImpl
    ): CommentsRepository
}
