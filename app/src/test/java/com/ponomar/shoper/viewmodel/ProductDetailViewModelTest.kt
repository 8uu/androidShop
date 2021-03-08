package com.ponomar.shoper.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.repository.CartRepository
import com.ponomar.shoper.ui.detail.ProductDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProductDetailViewModelTest:ApiAbstractViewModel() {
    private lateinit var repository: CartRepository
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setup(){
        repository = CartRepository(client, daoHolder)
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun plusQuantityInCartTest():Unit = runBlocking {
        whenever(cartDAO.incQuantity(1)).thenReturn(2)
        val observer: Observer<Int> = mock()
        val fetchedData = repository.incQuantityOfItemClick(
                1,
                onComplete = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.plusQuantityInCart(1)

        verify(observer).onChanged(2)
        verify(cartDAO, atLeastOnce()).incQuantity(1)
        fetchedData.removeObserver(observer)
    }

    @Test
    fun minusQuantityInCartTest(): Unit = runBlocking {
        whenever(cartDAO.decQuantity(1)).thenReturn(1)
        val observer: Observer<Int> = mock()
        val fetchedData = repository.decQuantityOfItemClick(
                1,
                onComplete = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.minusQuantityInCart(1)

        verify(observer).onChanged(1)
        verify(cartDAO, atLeastOnce()).decQuantity(1)
        fetchedData.removeObserver(observer)
    }
}