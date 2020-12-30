package com.ponomar.shoper.network

import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.model.UpdateEmailResponse
import com.ponomar.shoper.model.UserResponse
import com.ponomar.shoper.model.body.EmailBody
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.User
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/user/info/email/add")
    suspend fun updateUserInfo(@Body emailBody: EmailBody):ApiResponse<UpdateEmailResponse>

    @POST("/user/info")
    suspend fun fetchUserInfo(@Body tokenBody: TokenBody):ApiResponse<UserResponse>
}