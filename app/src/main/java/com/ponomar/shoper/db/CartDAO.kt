package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct
import com.ponomar.shoper.model.entities.Cart


@Dao
interface CartDAO {

    @Query("DELETE FROM cart;")
    suspend fun nukeTable()

    @Query("SELECT * from product inner join cart where product.id = cart.pid;")
    suspend fun getCart():List<CartInnerProduct>

    @Insert
    suspend fun insert(cart:Cart)

    @Update
    suspend fun update(cart: Cart)

    @Query("INSERT OR REPLACE into cart(`pid`,`quantity`) VALUES(:pid,coalesce((select quantity from cart where pid = :pid)+1,1))")
    suspend fun incQuantity(pid:Int):Int =  pid

    @Transaction
    suspend fun decQuantity(pid:Int):Int{
        decQuantityRaw(pid)
        clearEmptyRowsInCart()
        return pid
    }


    @Query("UPDATE cart set quantity = quantity - 1 where pid = :pid;")
    suspend fun decQuantityRaw(pid:Int):Int

    @Query("DELETE FROM cart where quantity = 0;")
    suspend fun clearEmptyRowsInCart()

}