package com.ponomar.shoper.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ponomar.shoper.model.entities.Address


@Dao
interface AddressDAO{

    @Insert
    fun insert(address:Address)

    @Delete
    fun delete(address: Address)

    @Query("SELECT * FROM address;")
    fun getAddresses():List<Address>
}