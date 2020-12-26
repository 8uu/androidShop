package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val client: Client,
    private val appDB: AppDB
){




    suspend fun initF():User {
        val user = User(8,"Test 4122","1337",null)
        appDB.getUserDao().insert(user)
        return user
    }

    suspend fun fetchUserInfo(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val user = appDB.getUserDao().getUser()
        if(user == null){

        }else {
            onSuccess()
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


}