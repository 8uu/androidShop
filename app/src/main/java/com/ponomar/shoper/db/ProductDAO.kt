package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.entities.Product


@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM product;")
    fun getProducts():List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products:List<Product>)

}