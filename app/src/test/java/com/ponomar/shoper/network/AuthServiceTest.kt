package com.ponomar.shoper.network

import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.model.body.CodeBody
import com.ponomar.shoper.model.body.PhoneBody
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class AuthServiceTest: ApiAbstractNetwork<AuthService>(){

    private lateinit var service: AuthService

    @Before
    fun initService(){
        service = createService(AuthService::class.java)
    }

    @Test
    fun sendUserDataToGenerateCodeTest()= runBlocking{
        enqueueResponse("/CodeResponse.json")
        val response = service.sendUserDataToGenerateCode(PhoneBody("88005553535"))
        val responseData = requireNotNull(response as ApiResponse.Success).data
        mockWebServer.takeRequest()

        assertEquals("code",1234,responseData!!.code)
        assertEquals("status",0,responseData.status)
    }

    @Test
    fun sendUserCodeToVerifyTest() = runBlocking{
        enqueueResponse("/TokenResponse.json")
        val response = service.sendUserCodeToVerify(CodeBody(1234,"88005553535","fio"))
        val responseData = requireNotNull(response as ApiResponse.Success).data

        assertEquals("token","TEST_TOKEN",responseData!!.token)
        assertEquals("status",0,responseData.status)
    }
}