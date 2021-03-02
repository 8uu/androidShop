package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnotherThingsRepository @Inject constructor(
        private val client: Client,
        private val appDB: AppDB
) {

    suspend fun fetchNews(
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow{
        client.fetchNews().suspendOnSuccess {
            Log.e("datanews",data!!.toString())
            if(data != null){
                if(data!!.status == 0){
                    emit(data!!.news)
                }else onError("Новостей нет")
            }else onError("NULL DATA")
            onComplete()
        }.onError { onError(message()) }
                .onException { onError(message()) }
    }

    suspend fun fetchAddresses(
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow{
        val addresses = appDB.getAddressDao().getAddresses()
        emit(addresses)
        onComplete()
    }

}