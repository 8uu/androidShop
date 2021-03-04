package com.ponomar.shoper.network

import com.nhaarman.mockitokotlin2.mock
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class ProductServiceTest:ApiAbstractNetwork<ProductService>() {

    private lateinit var service: ProductService
    private val client:Client = mock()

    @Before
    fun initService(){
        service = createService(ProductService::class.java)
    }

    @Test
    fun fetchProductList() = runBlocking {
        enqueueResponse("/ProductResponse.json")
        val response = service.fetchProducts()

        val responseData = (response as ApiResponse.Success).data
        mockWebServer.takeRequest()
        assertEquals(responseData!!.status,0)
        assertEquals(1,responseData.data!![0].id)
    }

}