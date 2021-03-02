package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.db.DaoHolder
import com.ponomar.shoper.network.Client
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepository @Inject constructor(
        private val client:Client,
        private val daoHolder: DaoHolder
) {

    suspend fun fetchCart(
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        val data = daoHolder.cartDAO.getCart()
        if(data.isEmpty()) onError("Пустая корзина")
        onComplete()
        emit(data)
    }

    suspend fun decQuantityOfItemClick(
            pid:Int,
            onComplete: () -> Unit
    ) = flow<Int> {
        emit(daoHolder.cartDAO.decQuantity(pid))
        onComplete()
    }

    suspend fun incQuantityOfItemClick(
            pid:Int,
            onComplete: () -> Unit
    ) = flow<Int>{
        emit(daoHolder.cartDAO.incQuantity(pid))
        onComplete()
    }


}