package com.ponomar.shoper.network

import com.ponomar.shoper.model.CodeResponse
import com.ponomar.shoper.model.TokenResponse
import com.ponomar.shoper.model.body.CodeBody
import com.ponomar.shoper.model.body.PhoneBody
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface AuthService {

    @POST("/auth/code")
    suspend fun sendUserDataToGenerateCode(@Body phoneBody:PhoneBody):ApiResponse<CodeResponse>

    @POST("/auth/login/code")
    suspend fun sendUserDataToGenerateCodeWhenUserTryToLogin(@Body phoneBody: PhoneBody):ApiResponse<CodeResponse>

    @POST("/auth/code/verify")
    suspend fun sendUserCodeToVerify(@Body codeBody: CodeBody):ApiResponse<TokenResponse>
}