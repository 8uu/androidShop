package com.ponomar.shoper.network

import com.ponomar.shoper.model.NewsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.POST

interface NewsService {

    @POST("/news")
    suspend fun fetchNews():ApiResponse<NewsResponse>
}