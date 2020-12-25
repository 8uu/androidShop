package com.ponomar.shoper.repository

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.network.Client
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val client: Client,
    private val appDB: AppDB
){


    init{

    }

    suspend fun initF(){

    }

    suspend fun fetchUserInfo(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val user = appDB.getUserDao().getUser()
        if(user == null){
            //TODO:
            onError("Not user")
            onSuccess()
        }else {
            onSuccess()
            emit(user)
        }
    }


}