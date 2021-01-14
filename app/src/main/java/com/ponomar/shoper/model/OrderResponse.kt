package com.ponomar.shoper.model

import com.ponomar.shoper.model.entities.Order

data class OrderResponse(
        val status:Int,
        val data:List<Order>

)