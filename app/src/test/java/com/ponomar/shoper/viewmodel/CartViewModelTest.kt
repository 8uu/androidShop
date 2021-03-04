package com.ponomar.shoper.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.CoroutineTestRule
import com.ponomar.shoper.repository.CartRepository
import com.ponomar.shoper.ui.cart.CartViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartViewModelTest: ApiAbstract() {
    private lateinit var repository: CartRepository
    private lateinit var viewModel: CartViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = CartRepository(client, daoHolder)
        viewModel = CartViewModel(repository, SavedStateHandle())
    }

    @Test
    fun fetchingIncStatusTest(): Unit = runBlocking {
        whenever(cartDAO.incQuantity(1)).thenReturn(1)
        val fetchedData: LiveData<Int> =
            repository.incQuantityOfItemClick(1,onComplete = {}).asLiveData()
        val observer: Observer<Int> = mock()
        fetchedData.observeForever(observer)

        viewModel.onPlusItemClick(1)
        verify(observer).onChanged(1)
        verify(cartDAO, atLeastOnce()).incQuantity(1)
    }

    @Test
    fun fetchingDecStatusTest():Unit = runBlocking{
        whenever(cartDAO.decQuantity(1)).thenReturn(0)
        val fetchedData: LiveData<Int> =
            repository.decQuantityOfItemClick(1, onComplete = {}).asLiveData()
        val observer: Observer<Int> = mock()
        fetchedData.observeForever(observer)

        viewModel.onMinusItemClick(1)
        verify(observer).onChanged(0)
        verify(cartDAO, atLeastOnce()).decQuantity(1)
    }
}