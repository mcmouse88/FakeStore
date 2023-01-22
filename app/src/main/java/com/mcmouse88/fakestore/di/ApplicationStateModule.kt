package com.mcmouse88.fakestore.di

import com.mcmouse88.fakestore.presentation.redux.ApplicationState
import com.mcmouse88.fakestore.presentation.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object ApplicationStateModule {

    @[Provides Singleton]
    fun providesApplicationStateStore(): Store<ApplicationState> {
        return Store(ApplicationState())
    }
}