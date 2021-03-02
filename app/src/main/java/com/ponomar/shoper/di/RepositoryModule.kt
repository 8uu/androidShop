package com.ponomar.shoper.di

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.db.CartDAO
import com.ponomar.shoper.db.DaoHolder
import com.ponomar.shoper.db.ProductDAO
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
    fun provideProductRepository(client: Client, daoHolder: DaoHolder):ProductRepository = ProductRepository(client, daoHolder)

    @Provides
    @ActivityRetainedScoped
    fun provideAuthRepository(client: Client, daoHolder: DaoHolder):AuthRepository = AuthRepository(client, daoHolder)

    @Provides
    @ActivityRetainedScoped
    fun provideOrderRepository(client: Client, daoHolder: DaoHolder):OrderRepository = OrderRepository(client, daoHolder)

    @Provides
    @ActivityRetainedScoped
    fun provideUserRepository(client: Client, daoHolder: DaoHolder):UserRepository = UserRepository(client, daoHolder)

    @Provides
    @ActivityRetainedScoped
    fun provideCartRepository(client: Client, daoHolder: DaoHolder):CartRepository = CartRepository(client, daoHolder)

    @Provides
    @ActivityRetainedScoped
    fun provideAnotherRepository(client: Client, daoHolder: DaoHolder):AnotherThingsRepository = AnotherThingsRepository(client, daoHolder)

}