package com.ponomar.shoper.repository

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val client:Client,
        private val appDB: AppDB
){
    suspend fun fetchUserInfo(
            token: String,
            onComplete: () -> Unit,
            onError: (String) -> Unit,
            isForceUpdate:Boolean  = false
    ) = flow {
        if(!isForceUpdate) {
            val user = appDB.getUserDao().getUser()
            if (user == null) {
                client.fetchUserData(token)
                        .suspendOnSuccess {
                            if (data != null) {
                                if (data!!.status == 0) {
                                    appDB.getUserDao().nukeTable()
                                    appDB.getUserDao().insert(data!!.data!!)
                                    emit(data!!.data!!)
                                } else {
                                    onError("STATUS:${data!!.status}")
                                }
                            } else onError("NULL DATA")
                            onComplete()
                        }.onError { onError(message()) }
                        .onException { onError(message()) }
            } else {
                onComplete()
                emit(user)
            }
        }else{
            client.fetchUserData(token)
                    .suspendOnSuccess {
                        if (data != null) {
                            if (data!!.status == 0) {
                                appDB.getUserDao().nukeTable()
                                appDB.getUserDao().insert(data!!.data!!)
                                emit(data!!.data!!)
                            } else {
                                onError("STATUS:${data!!.status}")
                            }
                        } else onError("NULL DATA")
                        onComplete()
                    }.onError { onError(message()) }
                    .onException { onError(message()) }
        }
    }

    suspend fun updateUserEmail(email:String,
                                token:String,
                                onComplete: () -> Unit,
                                onError: (String) -> Unit)= flow {
        client.updateUserEmail(token, email)
                .suspendOnSuccess {
                    if(data != null){
                        if(data!!.status == 0) {
//                                appDB.getUserDao().updateEmail(data!!.uid!!,email)
                            emit(data!!.status)
                        }
                        else{
                            onError("STATUS:${data!!.status}")
                        }
                    }else onError("ERROR")
                    onComplete()
                }.onError { onError(message()) }
                .onException { onError(message()) }
    }

}