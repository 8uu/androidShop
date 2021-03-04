package com.ponomar.shoper.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.nhaarman.mockitokotlin2.*
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.CoroutineTestRule
import com.ponomar.shoper.common.MockUtilUnit.mockCartInfoList
import com.ponomar.shoper.common.MockUtilUnit.mockEmdeddedProductList
import com.ponomar.shoper.common.MockUtilUnit.mockProductResponse
import com.ponomar.shoper.repository.ProductRepository
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct
import com.ponomar.shoper.ui.menu.MenuViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

class MenuViewModelTest: ApiAbstract() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var repository: ProductRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initObjects(){
        repository = ProductRepository(client, daoHolder)
        viewModel = MenuViewModel(repository, SavedStateHandle())
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchListOfProducts() = runBlocking{
        val mockProductResponse = mockProductResponse()
        val mockEmdeddedProductList = mockEmdeddedProductList()
        val mockCartInfoList = mockCartInfoList()

        whenever(productService.fetchProducts()).thenReturn(ApiResponse.of { Response.success(mockProductResponse) })
        whenever(cartDAO.getCartInfo()).thenReturn(mockCartInfoList)
        val observer: Observer<List<EmbeddedProduct>> = mock()
        val fetchedData = repository.fetchListOfProducts(
                onSuccess = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)

        viewModel.updateListOfProducts(0)
        delay(500L)

        verify(observer).onChanged(mockEmdeddedProductList)
        verify(productService, atLeastOnce()).fetchProducts()
        fetchedData.removeObserver(observer)
    }


}