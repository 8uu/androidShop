package com.ponomar.shoper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockOrderList
import com.ponomar.shoper.model.OrderResponse
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.Order
import com.ponomar.shoper.repository.OrderRepository
import com.ponomar.shoper.ui.order_history.OrderHistoryViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class OrderHistoryViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: OrderRepository
    private lateinit var viewModel: OrderHistoryViewModel

    @Before
    fun setup(){
        repository = OrderRepository(client, daoHolder)
        viewModel = OrderHistoryViewModel(repository, SavedStateHandle())
    }

    @Test
    fun fetchOrderHistoryTest(): Unit = runBlocking {
        val mockOrderList = mockOrderList()
        val mockTokenBody = TokenBody("TEST_TOKEN")
        val orderResponse = OrderResponse(0, mockOrderList)
        whenever(orderService.fetchOrders(mockTokenBody)).thenReturn(ApiResponse.of { Response.success(orderResponse) })
        val fetchedData: LiveData<List<Order>> = repository.fetchHistoryOfOrder(
                "TEST_TOKEN",
                onComplete = {},
                onError = {}
        ).asLiveData()
        val observer: Observer<List<Order>> = mock()
        fetchedData.observeForever(observer)
        viewModel.fetchHistoryOfOrder("TEST_TOKEN")
        verify(observer).onChanged(mockOrderList)
        verify(orderService, atLeastOnce()).fetchOrders(mockTokenBody)
    }
}