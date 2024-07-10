package com.example.myapp1.di

import com.example.myapp1.network.CommentsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun provideMyApi(): CommentsAPI {
        return Retrofit
            .Builder()
            .baseUrl(HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentsAPI::class.java)
    }
}