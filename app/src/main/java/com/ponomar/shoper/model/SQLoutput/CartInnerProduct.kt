package com.ponomar.shoper.model.SQLoutput

import androidx.room.Embedded
import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.entities.Product

data class CartInnerProduct(
        @Embedded val product:Product,
        @Embedded val cartInfo:Cart
)
