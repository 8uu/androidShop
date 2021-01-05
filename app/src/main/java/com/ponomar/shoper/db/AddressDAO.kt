package com.ponomar.shoper.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ponomar.shoper.model.entities.Address


@Dao
interface AddressDAO{

    @Insert
    suspend fun insert(address:Address)

    @Delete
    suspend fun delete(address: Address)

    @Query("SELECT * FROM address;")
    suspend fun getAddresses():List<Address>
}