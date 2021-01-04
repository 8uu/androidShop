package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.SQLoutput.CartInnerProduct
import com.ponomar.shoper.model.entities.Cart


@Dao
interface CartDAO {

    @Query("DELETE FROM cart;")
    fun nukeTable()

    @Query("SELECT * from product inner join cart where product.id = cart.pid;")
    fun getCart():List<CartInnerProduct>

    @Insert
    fun insert(cart:Cart)

    @Update
    fun update(cart: Cart)

    @Query("UPDATE cart set quantity = quantity + 1 where pid = :pid;")
    fun incQuantity(pid:Int):Int

    @Query("UPDATE cart set quantity = quantity - 1 where pid = :pid;")
    fun decQuantity(pid:Int):Int

}