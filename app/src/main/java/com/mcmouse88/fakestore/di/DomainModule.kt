package com.mcmouse88.fakestore.di

import com.mcmouse88.fakestore.data.ProductRepositoryImpl
import com.mcmouse88.fakestore.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface DomainModule {

    @[Binds Singleton]
    fun bindsProductRepository(impl: ProductRepositoryImpl): ProductRepository
}