package com.ponomar.shoper.network

import com.ponomar.shoper.model.body.OrderBody
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.Cart
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class OrderServiceTest:ApiAbstractNetwork<OrderService>() {

    private lateinit var service:OrderService

    @Before
    fun initService(){
        service = createService(OrderService::class.java)
    }

    @Test
    fun fetchOrdersTest() = runBlocking{
        enqueueResponse("/OrderResponse.json")
        val response = service.fetchOrders(TokenBody("TEST_TOKEN"))
        val data = requireNotNull(response as ApiResponse.Success).data
        val productData = data!!.data[0].products

        assertEquals("status",0,data.status)
        assertEquals("orderProductID",1,productData[0].id)
        assertEquals("addressID",1337,data.data[0].address.flat)
        assertEquals("orderID",1,data.data[0].id)
    }

    @Test
    fun requestOrderTest() = runBlocking {
        enqueueResponse("/StatusResponse.json")
        val response = service.requestOrder(OrderBody("TEST_TOKEN", Address("test","test","test",123), arrayListOf(Cart(1,1))))
        val data = requireNotNull(response as ApiResponse.Success).data!!

        assertEquals("status",0,data.status)
    }
}