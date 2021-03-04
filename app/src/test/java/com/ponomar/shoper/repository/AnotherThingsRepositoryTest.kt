package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.MockUtilUnit.mockAddress
import com.ponomar.shoper.common.MockUtilUnit.mockAddressList
import com.ponomar.shoper.common.MockUtilUnit.mockNewsList
import com.ponomar.shoper.model.NewsResponse
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.Response
import kotlin.time.ExperimentalTime

class AnotherThingsRepositoryTest: ApiAbstract() {
    private lateinit var repository: AnotherThingsRepository

    @Before
    fun initRepository(){
        repository = AnotherThingsRepository(client, daoHolder)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchNewsTest() = runBlocking{
        whenever(newsService.fetchNews())
                .thenReturn(ApiResponse.of { Response.success(NewsResponse(0,mockNewsList())) })
        repository.fetchNews(
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()[0]
            assertEquals("news id",1,item.id)
            assertEquals("type","large",item.type)
            expectComplete()
        }
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchAddressTest() = runBlockingTest {
        whenever(addressDAO.getAddresses()).thenReturn(mockAddressList())
        repository.fetchAddresses(
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()[0]
            assertEquals("address district","VO",item.district)
            assertEquals("flat",320,item.flat)
            expectComplete()
        }
        verify(addressDAO, atLeastOnce()).getAddresses()
    }
}