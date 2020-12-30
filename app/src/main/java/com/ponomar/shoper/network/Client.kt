package com.ponomar.shoper.network

import com.ponomar.shoper.model.*
import com.ponomar.shoper.model.body.CodeBody
import com.ponomar.shoper.model.body.EmailBody
import com.ponomar.shoper.model.body.PhoneBody
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.User
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class Client @Inject constructor(
    private val userService: UserService,
    private val addressService: AddressService,
    private val productService: ProductService,
    private val authService:AuthService) {


    suspend fun fetchProductsList() = productService.fetchProducts()
    suspend fun fetchUserData(token: String):ApiResponse<UserResponse>{
        val body = TokenBody(token)
        return userService.fetchUserInfo(body)
    }

    suspend fun sendUserDataToGenerateCode(phone: String):ApiResponse<CodeResponse>{
        val body = PhoneBody(phone)
        return authService.sendUserDataToGenerateCode(body)
    }

    suspend fun verifyCode(code:Int,phone:String,firstName:String):ApiResponse<TokenResponse>{
        val body = CodeBody(code,phone,firstName)
        return authService.sendUserCodeToVerify(body)
    }

    suspend fun updateUserEmail(token:String,email:String):ApiResponse<UpdateEmailResponse>{
        val body = EmailBody(email, token)
        return userService.updateUserInfo(body)
    }





}