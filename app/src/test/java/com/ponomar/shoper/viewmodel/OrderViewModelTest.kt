package com.ponomar.shoper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.*
import com.ponomar.shoper.common.MockUtilUnit
import com.ponomar.shoper.common.MockUtilUnit.mockAddress
import com.ponomar.shoper.common.MockUtilUnit.mockAddressList
import com.ponomar.shoper.common.MockUtilUnit.mockCartInfoList
import com.ponomar.shoper.common.MockUtilUnit.mockListOfProduct
import com.ponomar.shoper.common.MockUtilUnit.mockOrder
import com.ponomar.shoper.common.MockUtilUnit.mockOrderList
import com.ponomar.shoper.model.OrderResponse
import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.model.body.OrderBody
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.Order
import com.ponomar.shoper.repository.AnotherThingsRepository
import com.ponomar.shoper.repository.OrderRepository
import com.ponomar.shoper.ui.order.OrderViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class OrderViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: OrderRepository
    private lateinit var addressRepository: AnotherThingsRepository
    private lateinit var viewModel: OrderViewModel

    @Before
    fun setup(){
        repository = OrderRepository(client, daoHolder)
        addressRepository = AnotherThingsRepository(client, daoHolder)
        viewModel = OrderViewModel(repository,addressRepository, SavedStateHandle())

    }

    @Test
    fun requestOrderTest(): Unit = runBlocking {
        val mockStatusResponse = StatusResponse(0)
        val mockAddress = mockAddress()
        val mockOrderBody = OrderBody("TEST_TOKEN",mockAddress, mockCartInfoList())
        whenever(cartDAO.getCartInfo()).thenReturn(mockCartInfoList())
        whenever(orderService.requestOrder(mockOrderBody)).thenReturn(ApiResponse.of { Response.success(mockStatusResponse) })
        val observer: Observer<Int> = mock()
        val fetchedData: LiveData<Int> = repository.makeOrderRequest(
                "TEST_TOKEN",
                mockAddress,
                onComplete = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.makeOrderRequest(mockAddress.district,mockAddress.street,mockAddress.house, mockAddress.flat, "TEST_TOKEN")
        verify(orderService, atLeastOnce()).requestOrder(mockOrderBody)
        verify(cartDAO, atLeastOnce()).getCartInfo()
        verify(observer).onChanged(mockStatusResponse.status)
        fetchedData.removeObserver(observer)
    }
}