package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val client: Client,
    private val appDB: AppDB
){


    suspend fun fetchUserInfo(
            token: String,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        val user = appDB.getUserDao().getUser()
        if(user == null){
            client.fetchUserData(token)
                    .suspendOnSuccess {
                        if(data!= null){
                            if(data!!.status == 0){
                                appDB.getUserDao().nukeTable()
                                appDB.getUserDao().insert(data!!.data!!)
                                emit(data!!.data!!)
                            }else{
                                onError("STATUS:${data!!.status}")
                            }
                        }else onError("NULL DATA")
                        onComplete()
                    }.onError { onError(message()) }
                    .onException { onError(message()) }
        }else {
            onComplete()
            emit(user)
        }
    }

    suspend fun fetchListOfProducts(
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        val response = client.fetchProductsList()
        response.suspendOnSuccess {
            when {
                data == null -> {
                    onError("Null data")
                }
                data!!.status != 0 -> {
                    onError("STATUS:${data!!.status}")
                }
                else -> {
                    emit(data!!.data!!)
                }
            }
            onSuccess()
        }
                .onError {
                    onError(message())
                    onSuccess()
                }
                .onException {
                    onError(message())
                    onSuccess()}
    }

    suspend fun sendUserDataToGenerateAuthCode(
            phone:String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.sendUserDataToGenerateCode(phone)
                .suspendOnSuccess {
                    if(data != null) {
                        if (data!!.status != 11) {
                            onError("ERROR.STATUS:${data!!.status}")
                        }else {
                            emit(data!!.code)
                        }
                    }else onError("ERROR")
                    onSuccess()
                }.onError { onError(message())  }
                .onException { onError(message()) }
                .onFailure { onSuccess() }

    }


    //TODO:SAME FUNCTION LAMBDA REPEAT BLOCK
    suspend fun verifyCode(
            phone:String,
            firstName:String,
            code:Int,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.verifyCode(code, phone, firstName)
                .suspendOnSuccess {
                    if(data != null) {
                        if (data!!.status != 30) {
                            onError("ERROR.STATUS:${data!!.status}")
                        }else {
                            appDB.getUserDao().nukeTable()
//                            appDB.getUserDao().apply {
//                                nukeTable()
//                                insert(User(data!!.uid!!,firstName,phone,null))
//                            }
                            emit(data!!.token!!)
                        }
                    }else onError("ERROR")
                    onSuccess()
                }.onError { onError(message()) }
                .onException { onError(message()) }
                .onFailure { onSuccess() }}


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
