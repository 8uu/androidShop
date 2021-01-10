package com.ponomar.shoper.extensions

import android.util.Log
import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.model.sqlOutput.CartInnerProduct

fun convertProductListAndCartInfoListToCartInnerProductList(products:List<Product>,cart:List<Cart>):List<CartInnerProduct>{
    val listWithFinalData:MutableList<CartInnerProduct> = mutableListOf()
    outer@ for(product in products){
        for(cartItem in cart){
            if(cartItem.pid == product.id){
                listWithFinalData.add(CartInnerProduct(product,cartItem))
                continue@outer
            }
        }
        listWithFinalData.add(CartInnerProduct(product,null))
    }
    return listWithFinalData
}