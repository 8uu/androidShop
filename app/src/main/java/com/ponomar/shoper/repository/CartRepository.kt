package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepository @Inject constructor(
        private val client:Client,
        private val appDB: AppDB
) {

    suspend fun fetchCart(
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        val data = appDB.getCartDao().getCart()
        if(data.isEmpty()) onError("Пустая корзина")
        Log.e("data",data.toString())
        onComplete()
        emit(data)
    }

    suspend fun decQuantityOfItemClick(
            pid:Int,
            onComplete: () -> Unit
    ) = flow<Int> {
        emit(appDB.getCartDao().decQuantity(pid))
        onComplete()
    }

    suspend fun incQuantityOfItemClick(
            pid:Int,
            onComplete: () -> Unit
    ) = flow<Int>{
        Log.e("asd","inc")
        emit(appDB.getCartDao().incQuantity(pid))
        onComplete()
    }


}