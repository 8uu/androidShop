package com.ponomar.shoper.network

import com.ponomar.shoper.model.OrderResponse
import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.model.body.OrderBody
import com.ponomar.shoper.model.body.TokenBody
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {

    @POST("/orders/add")
    suspend fun requestOrder(@Body body:OrderBody):ApiResponse<StatusResponse>

    @POST("/orders")
    suspend fun fetchOrders(@Body body:TokenBody):ApiResponse<OrderResponse>
}