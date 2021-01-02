package com.ponomar.shoper.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.ponomar.shoper.model.SQLoutput.CartInnerProduct


@Dao
interface CartDAO {

    @Query("DELETE FROM cart;")
    fun nukeTable()

    @Query("SELECT * from product inner join cart where product.id = cart.pid;")
    fun getCart():List<CartInnerProduct>
}