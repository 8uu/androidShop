package com.ponomar.shoper.db

import androidx.room.*
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct
import com.ponomar.shoper.model.entities.Cart


@Dao
interface CartDAO {

    @Query("DELETE FROM cart;")
    suspend fun nukeTable()

    @Query("SELECT * from product inner join cart where product.id = cart.pid;")
    suspend fun getCart():List<EmbeddedProduct>

    @Query("SELECT * from cart;")
    suspend fun getCartInfo():List<Cart>

    @Insert
    suspend fun insert(cart:Cart)

    @Update
    suspend fun update(cart: Cart)

    @Deprecated(message = "Doesn't work query(", replaceWith = ReplaceWith("incQuantity"))
    @Query("INSERT OR REPLACE into cart(`pid`,`quantity`) VALUES(:pid,coalesce((select quantity from cart where pid = :pid)+1,1));")
    suspend fun incQuantityQuery(pid:Int):Int =  pid

    @Query("SELECT quantity from cart where pid = :pid;")
    suspend fun getQuantityInCart(pid: Int):Int?



    @Transaction
    suspend fun incQuantity(pid:Int):Int{
        val quantity = getQuantityInCart(pid)
        if(quantity == null) insert(Cart(pid,1))
        else incQuantityRaw(pid)
        return pid
    }

    @Transaction
    suspend fun decQuantity(pid:Int):Int{
        decQuantityRaw(pid)
        clearEmptyRowsInCart()
        return pid
    }


    @Query("UPDATE cart set quantity = quantity - 1 where pid = :pid;")
    suspend fun decQuantityRaw(pid:Int):Int

    @Query("UPDATE cart set quantity = quantity + 1 where pid = :pid;")
    suspend fun incQuantityRaw(pid:Int):Int

    @Query("DELETE FROM cart where quantity = 0;")
    suspend fun clearEmptyRowsInCart()

}