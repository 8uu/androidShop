package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.extensions.convertProductListAndCartInfoListToCartInnerProductList
import com.ponomar.shoper.model.entities.Address
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


    suspend fun fetchListOfProducts(
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
//        val dataFromDB = appDB.getProductDao().getProducts()
//        if(dataFromDB.isEmpty()) {
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
                        appDB.getProductDao().insertAll(data!!.data!!)
                        val cartData = appDB.getCartDao().getCartInfo()
                        Log.e("cart",cartData.toString())
                        emit(convertProductListAndCartInfoListToCartInnerProductList(data!!.data!!,cartData))
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
                        onSuccess()
                    }
//        }else{
//            val dataCart = appDB.getCartDao().getCartInfo()
//            emit(convertProductListAndCartInfoListToCartInnerProductList(dataFromDB,dataCart))
//            onSuccess()
//        }
    }

    suspend fun sendUserDataToGenerateAuthCode(
            phone:String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.sendUserDataToGenerateCode(phone)
                .suspendOnSuccess {
                    if(data != null) {
                        if (data!!.status != 0) {
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

    suspend fun sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
            phone: String,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow{
        client.sendUserDataToGenerateCodeWhenUserTryToLogin(phone)
                .suspendOnSuccess {
                    if(data!=null){
                        when(data!!.status){
                            61 ->{onError("Вы незарегистрированны")}
                            11 -> {onError("Произошла ошибка на сервере")}
                            else -> {
                                emit(data!!.code)
                            }
                        }
                        onComplete()
                    }else {onError("Попробуйте позже")}
                }.onError { onError(message()) }
                .onException { onError(message()) }
                .onFailure { onComplete() }
    }



    suspend fun verifyCode(
            phone:String,
            firstName:String? = null,
            code:Int,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.verifyCode(code, phone, firstName)
                .suspendOnSuccess {
                    if(data != null) {
                        if (data!!.status != 30) {
                            onError("ERROR.STATUS:${data!!.status}")
                        }else {
                            appDB.getProductDao().nukeTable()
                            appDB.getUserDao().nukeTable() //TODO:REFACTOR CHECKING USER AUTH ALREADY
                            appDB.getCartDao().nukeTable()
//                            appDB.getUserDao().apply {
//                                nukeTable()
//                                insert(User(data!!.uid!!,firstName,phone,null))
//                            }
                            emit(data!!.token!!)
                        }
                    }else onError("ERROR")
                    onComplete()
                }.onError { onError(message()) }
                .onException { onError(message()) }
                .onFailure { onComplete() }}


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


//    suspend fun verifyCodeWhenUserTryToLogin(
//            code:Int,
//            phone: String,
//            onComplete: () -> Unit,
//            onError: (String) -> Unit) = flow {
//                client.verifyCode(code, phone)
//                        .suspendOnSuccess {
//                            if(data!!.status != 30){
//                                onError("ERROR.STATUS:${data!!.status}")
//                            }else{
//                                appDB.getProductDao().nukeTable()
//                                appDB.getCartDao().nukeTable()
//                                appDB.getUserDao().nukeTable()
//                                emit(data!!.token!!)
//                            }
//                        }.onException { onError(message()) }
//                        .onError { onError(message()) }
//                        .onFailure { onComplete() }
//            }

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


    suspend fun makeOrderRequest(
        token:String,
        address: Address,
        onComplete: () -> Unit,
        onError: (String) -> Unit
        ) = flow<Int>{
            val cart = appDB.getCartDao().getCartInfo()
            appDB.getAddressDao().insert(address)
            client.requestOrder(
                token,
                address,
                cart
            ).suspendOnSuccess {
                val status = data!!.status
                if(status == 0) appDB.getCartDao().nukeTable()
                emit(status)
                onComplete()
            }.onError { onError(message()) }
                .onException { onError(message()) }
    }

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

