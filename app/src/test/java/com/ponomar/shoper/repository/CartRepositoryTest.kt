package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockProduct
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CartRepositoryTest:ApiAbstract() {
    private lateinit var repository: CartRepository

    @Before
    fun initRepository(){
        repository = CartRepository(client, daoHolder)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchCartTest():Unit = runBlocking{
        whenever(cartDAO.getCart()).thenReturn(arrayListOf(EmbeddedProduct(mockProduct(),null)))
        repository.fetchCart(
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()
            assertEquals(1,item[0].product.id)
            assertEquals(800,item[0].product.weight)
            expectComplete()
        }
        verify(cartDAO, atLeastOnce()).getCart()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun decQuantityOfItemClickTest():Unit = runBlocking{
        whenever(cartDAO.decQuantity(1)).thenReturn(1)
        whenever(cartDAO.incQuantity(1)).thenReturn(2)

        repository.incQuantityOfItemClick(1,onComplete = {})
                .test{
                    val item = expectItem()
                    assertEquals(2,item)
                    expectComplete()
                }
        repository.decQuantityOfItemClick(1, onComplete = {})
                .test {
                    val item = expectItem()
                    assertEquals(1,item)
                    expectComplete()
                }
        verify(cartDAO, atLeastOnce()).decQuantity(1)
        verify(cartDAO, atLeastOnce()).incQuantity(1)
    }
}