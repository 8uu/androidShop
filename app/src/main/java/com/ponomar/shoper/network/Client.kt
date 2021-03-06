package com.ponomar.shoper.network

import com.ponomar.shoper.model.*
import com.ponomar.shoper.model.body.*
import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.Cart
import com.ponomar.shoper.model.entities.User
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class Client @Inject constructor(
    private val userService: UserService,
    private val productService: ProductService,
    private val authService:AuthService,
    private val orderService: OrderService,
    private val newsService: NewsService) {


    suspend fun fetchProductsList() = productService.fetchProducts()
    suspend fun fetchUserData(token: String):ApiResponse<UserResponse>{
        val body = TokenBody(token)
        return userService.fetchUserInfo(body)
    }

    suspend fun sendUserDataToGenerateCode(phone: String):ApiResponse<CodeResponse>{
        val body = PhoneBody(phone)
        return authService.sendUserDataToGenerateCode(body)
    }

    suspend fun sendUserDataToGenerateCodeWhenUserTryToLogin(phone: String):ApiResponse<CodeResponse>{
        val body = PhoneBody(phone)
        return authService.sendUserDataToGenerateCodeWhenUserTryToLogin(body)
    }

    suspend fun verifyCode(code:Int,phone:String,firstName:String?):ApiResponse<TokenResponse>{
        val body = CodeBody(code,phone,firstName)
        return authService.sendUserCodeToVerify(body)
    }

    suspend fun updateUserEmail(token:String,email:String):ApiResponse<UpdateEmailResponse>{
        val body = EmailBody(email, token)
        return userService.updateUserInfo(body)
    }

    suspend fun requestOrder(token:String,address:Address,cart:List<Cart>):ApiResponse<StatusResponse>{
        val body = OrderBody(token,address,cart)
        return orderService.requestOrder(body)
    }

    suspend fun fetchNews():ApiResponse<NewsResponse> = newsService.fetchNews()

    suspend fun fetchHistoryOfOrder(token:String):ApiResponse<OrderResponse>{
        val body = TokenBody(token)
        return orderService.fetchOrders(body)
    }





}