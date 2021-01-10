package com.ponomar.shoper.model.sqlOutput

import androidx.room.Embedded
import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.entities.Product

data class CartInnerProduct(
        @Embedded val product:Product,
        @Embedded var cartInfo:Cart?
)
