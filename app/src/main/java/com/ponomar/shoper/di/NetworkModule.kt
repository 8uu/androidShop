package com.ponomar.shoper.di

import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.network.AddressService
import com.ponomar.shoper.network.Client
import com.ponomar.shoper.network.ProductService
import com.ponomar.shoper.network.UserService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{


    @Provides
    @Singleton
    fun provideOkHttpClass():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .client(okHttpClient)
            .baseUrl("https://apiforlearning.heroku.com")
            .build()
    }


    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit):UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit):ProductService = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideAddressService(retrofit: Retrofit):AddressService = retrofit.create(AddressService::class.java)


    @Provides
    @Singleton
    fun provideClient(userService: UserService,
                      addressService: AddressService,
                      productService: ProductService):Client = Client(userService,addressService, productService)

}