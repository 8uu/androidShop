package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.entities.Product


@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM product;")
    suspend fun getProducts():List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products:List<Product>)

    @Query("DELETE FROM product;")
    suspend fun nukeTable()

}