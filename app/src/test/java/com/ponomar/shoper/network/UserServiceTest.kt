package com.ponomar.shoper.network

import com.ponomar.shoper.model.body.EmailBody
import com.ponomar.shoper.model.body.TokenBody
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

class UserServiceTest:ApiAbstractNetwork<UserService>() {

    private lateinit var service:UserService

    @Before
    fun initService(){
        service = createService(UserService::class.java)
    }

    @Test
    fun updateUserEmailTest() = runBlocking{
        enqueueResponse("/UpdateEmailResponse.json")
        val response = service.updateUserInfo(EmailBody("email@test.com","TEST_TOKEN"))
        val responseData = requireNotNull(response as ApiResponse.Success).data!!
        mockWebServer.takeRequest()

        assertEquals("status",0,responseData.status)
    }

    @Test
    fun fetchUserInfoTest() = runBlocking{
        enqueueResponse("/UserResponse.json")
        val response = service.fetchUserInfo(TokenBody("TEST_TOKEN"))
        val data = requireNotNull(response as ApiResponse.Success).data!!
        mockWebServer.takeRequest()

        assertEquals("userID",1,data.data!!.id)
        assertEquals("status",0,data.status)
        assertNull("emailNull",data.data!!.email)
    }
}