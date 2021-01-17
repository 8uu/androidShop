package com.ponomar.shoper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ponomar.shoper.model.entities.*
import com.ponomar.shoper.model.entities.converters.ImageConverters


@Database(entities = [Address::class, Product::class, User::class,Cart::class],version = 1,exportSchema = false)
@TypeConverters(value = [ImageConverters::class])
abstract class AppDB:RoomDatabase() {

    abstract fun getUserDao():UserDAO
    abstract fun getProductDao():ProductDAO
    abstract fun getAddressDao():AddressDAO
    abstract fun getCartDao():CartDAO

}