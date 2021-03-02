package com.ponomar.shoper.di

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import com.ponomar.shoper.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {


    @Provides
    @ActivityRetainedScoped
    fun provideProductRepository(client: Client,appDB: AppDB):ProductRepository = ProductRepository(client, appDB)

    @Provides
    @ActivityRetainedScoped
    fun provideAuthRepository(client: Client,appDB: AppDB):AuthRepository = AuthRepository(client, appDB)

    @Provides
    @ActivityRetainedScoped
    fun provideOrderRepository(client: Client,appDB: AppDB):OrderRepository = OrderRepository(client, appDB)

    @Provides
    @ActivityRetainedScoped
    fun provideUserRepository(client: Client,appDB: AppDB):UserRepository = UserRepository(client, appDB)

    @Provides
    @ActivityRetainedScoped
    fun provideCartRepository(client: Client,appDB: AppDB):CartRepository = CartRepository(client, appDB)

    @Provides
    @ActivityRetainedScoped
    fun provideAnotherRepository(client: Client,appDB: AppDB):AnotherThingsRepository = AnotherThingsRepository(client, appDB)

}