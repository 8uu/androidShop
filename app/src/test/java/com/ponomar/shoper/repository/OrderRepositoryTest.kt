package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.MockUtilUnit.mockAddress
import com.ponomar.shoper.common.MockUtilUnit.mockCartInfoList
import com.ponomar.shoper.common.MockUtilUnit.mockOrderList
import com.ponomar.shoper.model.OrderResponse
import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.model.body.OrderBody
import com.ponomar.shoper.model.body.TokenBody
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.Response
import kotlin.time.ExperimentalTime

class OrderRepositoryTest: ApiAbstract() {
    private lateinit var repository: OrderRepository

    @Before
    fun initRepository(){
        repository = OrderRepository(client,daoHolder)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun makeOrderRequestTest() = runBlocking{
        val mockCartInfo = mockCartInfoList()
        val mockAddress = mockAddress()
        val mockOrderBody = OrderBody("TEST_TOKEN",mockAddress,mockCartInfo)

        whenever(cartDAO.getCartInfo()).thenReturn(mockCartInfo)
        whenever(orderService.requestOrder(mockOrderBody)).thenReturn(ApiResponse.of { Response.success(StatusResponse(0)) })
        repository.makeOrderRequest(
                "TEST_TOKEN",
                mockAddress,
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()
            assertEquals(0,item)
            expectComplete()
        }
        verify(cartDAO, atLeastOnce()).getCartInfo()
        verify(orderService, atLeastOnce()).requestOrder(mockOrderBody)
        verify(addressDAO, atLeastOnce()).insert(mockAddress)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchHistoryOfOrderTest():Unit = runBlocking {
        val mockTokenBody = TokenBody("TEST_TOKEN")
        val mockOrderResponse = OrderResponse(0, mockOrderList())

        whenever(orderService.fetchOrders(mockTokenBody)).thenReturn(ApiResponse.of { Response.success(mockOrderResponse) })
        repository.fetchHistoryOfOrder(
                "TEST_TOKEN",
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()[0]
            assertEquals("id",1,item.id)
            assertEquals("address flat",320,item.address.flat)
            assertEquals("product id",1,item.products[0].id)
            assertEquals("order status",1,item.status)
            expectComplete()
        }
        verify(orderService, atLeastOnce()).fetchOrders(mockTokenBody)
    }
}