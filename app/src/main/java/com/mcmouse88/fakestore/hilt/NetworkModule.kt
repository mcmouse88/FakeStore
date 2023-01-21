package com.mcmouse88.fakestore.hilt

import com.mcmouse88.fakestore.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    private const val DURATION = 15L

    @[Provides Singleton]
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(DURATION, TimeUnit.SECONDS)
            .readTimeout(DURATION, TimeUnit.SECONDS)
            .writeTimeout(DURATION, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton]
    fun providesProductService(): ProductService {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}