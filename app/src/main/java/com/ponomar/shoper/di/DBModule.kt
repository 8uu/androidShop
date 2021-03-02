package com.ponomar.shoper.di

import android.app.Application
import androidx.room.Room
import com.ponomar.shoper.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {


    @Provides
    @Singleton
    fun provideAppDatabase(application: Application):AppDB{
        return Room.databaseBuilder(application,AppDB::class.java,"Shop.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDB: AppDB) = appDB.getUserDao()

    @Provides
    @Singleton
    fun provideAddressDao(appDB: AppDB) = appDB.getAddressDao()

    @Provides
    @Singleton
    fun provideProductDao(appDB: AppDB) = appDB.getProductDao()

    @Provides
    @Singleton
    fun provideCartDao(appDB: AppDB) = appDB.getCartDao()

    @Provides
    @Singleton
    fun provideDaoHolder(userDAO: UserDAO,productDAO: ProductDAO,cartDAO: CartDAO,addressDAO: AddressDAO) = DaoHolder(addressDAO, cartDAO, productDAO, userDAO)


}