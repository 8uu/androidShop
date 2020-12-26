package com.ponomar.shoper.di

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import com.ponomar.shoper.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {


    @Provides
    @ActivityRetainedScoped
    fun provideRepository(client: Client,appDB: AppDB):MainRepository = MainRepository(client, appDB)
}