package com.ponomar.shoper.repository

import android.util.Log
import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.extensions.convertProductListAndCartInfoListToCartInnerProductList
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
        private val client: Client,
        private val appDB: AppDB
) {

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

}