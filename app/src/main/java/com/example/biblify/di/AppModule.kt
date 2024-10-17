package com.example.biblify.di

import com.example.biblify.model.BiblifyBooks
import com.example.biblify.network.BiblifyAPI
import com.example.biblify.repository.BiblifyRepository
import com.example.biblify.utils.Constants
import com.example.biblify.utils.Constants.BASE_URL
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

    @Singleton
    @Provides
    fun providesBiblifyRepository(api: BiblifyAPI) = BiblifyRepository(api)

    @Singleton
    @Provides
    fun provideBooks(): BiblifyAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BiblifyAPI::class.java)
    }
}