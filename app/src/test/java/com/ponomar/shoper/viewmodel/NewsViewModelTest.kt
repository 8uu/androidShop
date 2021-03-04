package com.ponomar.shoper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockNewsList
import com.ponomar.shoper.model.NewsResponse
import com.ponomar.shoper.model.entities.News
import com.ponomar.shoper.repository.AnotherThingsRepository
import com.ponomar.shoper.ui.news.NewsViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: AnotherThingsRepository
    private lateinit var viewModelTest: NewsViewModel

    @Before
    fun setup(){
        repository = AnotherThingsRepository(client, daoHolder)
        viewModelTest = NewsViewModel(repository, SavedStateHandle())
    }

    @Test
    fun fetchNewsTest():Unit = runBlocking {
        val mockNewsList = mockNewsList()
        whenever(newsService.fetchNews()).thenReturn(ApiResponse.of { Response.success(NewsResponse(0,mockNewsList)) })
        val fetchedData:LiveData<List<News>> = repository.fetchNews(
                onComplete = {},
                onError = {}
        ).asLiveData()
        val observer:Observer<List<News>> = mock()
        fetchedData.observeForever(observer)
        viewModelTest.fetchNewsAt(0)
        verify(newsService, atLeastOnce()).fetchNews()
        verify(observer).onChanged(mockNewsList)
    }
}