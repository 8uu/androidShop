package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.entities.Address


@Dao
interface AddressDAO{

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(address:Address)

    @Delete
    suspend fun delete(address: Address)

    @Query("SELECT * FROM address;")
    suspend fun getAddresses():List<Address>

}