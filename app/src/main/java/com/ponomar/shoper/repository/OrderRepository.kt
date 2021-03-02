package com.ponomar.shoper.repository

import com.ponomar.shoper.db.DaoHolder
import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
        private val client:Client,
        private val daoHolder: DaoHolder
) {
    suspend fun makeOrderRequest(
            token:String,
            address: Address,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow<Int>{
        val cart = daoHolder.cartDAO.getCartInfo()
        daoHolder.addressDAO.insert(address)
        client.requestOrder(
                token,
                address,
                cart
        ).suspendOnSuccess {
            val status = data!!.status
            if(status == 0) daoHolder.cartDAO.nukeTable()
            emit(status)
            onComplete()
        }.onError { onError(message()) }
                .onException { onError(message()) }
    }

    suspend fun fetchHistoryOfOrder(
            token:String,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow{
        client.fetchHistoryOfOrder(token)
                .suspendOnSuccess {
                    if(data != null){
                        if(data!!.status == 3){
                            onError("У вас нет заказов")
                        }else{
                            emit(data!!.data)
                        }
                    }
                    onComplete()
                }.onError {
                    onError(message())
                }
                .onException {
                    onError(message())
                }
    }


}