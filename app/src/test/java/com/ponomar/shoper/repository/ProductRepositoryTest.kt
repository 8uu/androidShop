package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit
import com.ponomar.shoper.model.ProductResponse
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class ProductRepositoryTest: ApiAbstract() {

    private lateinit var repository: ProductRepository

    @Before
    fun initRepository(){
        repository = ProductRepository(client,daoHolder)
    }

    @ExperimentalTime
    @Test
    fun fetchProductsTest()= runBlocking {
        val mockProductResponse  = ProductResponse(0,MockUtilUnit.mockListOfProduct())

        whenever(productDAO.getProducts()).thenReturn(emptyList())
        whenever(productService.fetchProducts()).thenReturn(ApiResponse.of { Response.success(mockProductResponse) })
        whenever(cartDAO.getCartInfo()).thenReturn(emptyList())

        repository.fetchListOfProducts(
                onSuccess = {},
                onError = {},
        ).test{
            val item = expectItem()
            assertEquals("status",item[0].product.id,1)
            assertEquals("weight",item[0].product.weight,800)
            expectComplete()
        }

//        verify(productDAO, atLeastOnce()).getProducts()
        verify(productService, atLeastOnce()).fetchProducts()
        verify(productDAO, atLeastOnce()).insertAll(mockProductResponse.data!!)
    }


}