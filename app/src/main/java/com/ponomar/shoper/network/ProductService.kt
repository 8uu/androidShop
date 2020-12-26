package com.ponomar.shoper.network

import com.ponomar.shoper.model.ProductResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.POST

interface ProductService {

    @POST("/products")
    suspend fun fetchProducts():ApiResponse<ProductResponse>
}