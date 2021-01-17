package com.ponomar.shoper.extensions

import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.entities.Product
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct

fun convertProductListAndCartInfoListToCartInnerProductList(products:List<Product>,cart:List<Cart>):List<EmbeddedProduct>{
    val listWithFinalData:MutableList<EmbeddedProduct> = mutableListOf()
    outer@ for(product in products){
        for(cartItem in cart){
            if(cartItem.pid == product.id){
                listWithFinalData.add(EmbeddedProduct(product,cartItem))
                continue@outer
            }
        }
        listWithFinalData.add(EmbeddedProduct(product,null))
    }
    return listWithFinalData
}