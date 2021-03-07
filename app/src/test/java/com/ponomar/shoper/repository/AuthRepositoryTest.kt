package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.MockUtilUnit.mockUser
import com.ponomar.shoper.model.CodeResponse
import com.ponomar.shoper.model.TokenResponse
import com.ponomar.shoper.model.body.CodeBody
import com.ponomar.shoper.model.body.PhoneBody
import com.ponomar.shoper.model.body.TokenBody
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.Response
import kotlin.time.ExperimentalTime

class AuthRepositoryTest: ApiAbstract() {
    private lateinit var repository: AuthRepository

    private val mockPhone = "8(996)-730-50-22"
    private val mockPhoneBody = PhoneBody(mockPhone)

    @Before
    fun setup(){
        repository = AuthRepository(client, daoHolder)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun sendUserDataToGenerateCodeTest():Unit = runBlocking{
        whenever(authService.sendUserDataToGenerateCode(mockPhoneBody)).thenReturn(ApiResponse.of { Response.success(CodeResponse(0,1337)) })
        repository.sendUserDataToGenerateAuthCode(
                phone = mockPhone,
                onSuccess = {},
                onError = {}
        ).test{
            val item = expectItem()
            assertEquals(1337,item)
            expectComplete()
        }
        whenever(authService.sendUserDataToGenerateCode(mockPhoneBody)).thenReturn(ApiResponse.of { Response.success(CodeResponse(22,1337)) })
        val mockErrorLambda:(String) -> Unit = mock()
        repository.sendUserDataToGenerateAuthCode(
                phone = mockPhone,
                onSuccess = {},
                onError =  mockErrorLambda
        ).test {
            expectComplete()
        }
        verify(mockErrorLambda, atLeastOnce()).invoke("На этот номер уже зарегистрирован аккаунт")
        verify(authService, atLeast(2)).sendUserDataToGenerateCode(mockPhoneBody)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun sendUserDataToGenerateCodeWhenUserTryToLogin():Unit = runBlocking {
        whenever(authService
                .sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody))
                .thenReturn(ApiResponse.of { Response.success(CodeResponse(0,1337)) }
        )
        repository.sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
                phone = mockPhone,
                onComplete = {},
                onError = {}
        ).test {
            val item = expectItem()
            assertEquals(1337,item)
            expectComplete()
        }
        val mockErrorLambda:(String) -> Unit = mock()
        whenever(authService
                .sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody))
                .thenReturn(ApiResponse.of { Response.success(CodeResponse(61,1335)) })
        repository.sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
                phone = mockPhone,
                onComplete = {},
                onError = mockErrorLambda
        ).test{expectComplete()}
        verify(mockErrorLambda, atLeastOnce()).invoke("Вы незарегистрированны")
        whenever(authService
                .sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody))
                .thenReturn(ApiResponse.of { Response.success(CodeResponse(11,1336)) })
        repository.sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
                phone = mockPhone,
                onComplete = {},
                onError = mockErrorLambda
        ).test { expectComplete() }
        verify(mockErrorLambda, atLeastOnce()).invoke("Произошла ошибка на сервере")
        verify(authService, atLeast(3)).sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody)

    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun verifyCodeTest():Unit = runBlocking{
        val mockCodeBody = CodeBody(1337,mockPhone,null)
        val mockTokenResponse = TokenResponse(30,"TEST_TOKEN",1)
        val mockUser = mockUser()

        whenever(authService
                .sendUserCodeToVerify(mockCodeBody))
                .thenReturn(ApiResponse.of { Response.success(mockTokenResponse) })
        whenever(userDAO.getUserById(1)).thenReturn(null)
        repository.verifyCode(
                code = 1337,
                phone = mockPhone,
                onComplete = {},
                onError = {}
        ).test{
            val item = expectItem()
            assertEquals("TEST_TOKEN",item)
            expectComplete()
        }

        verify(userDAO, atLeastOnce()).nukeTable()

        whenever(authService
                .sendUserCodeToVerify(mockCodeBody))
                .thenReturn(ApiResponse.of { Response.success(mockTokenResponse) })
        whenever(userDAO.getUserById(1)).thenReturn(mockUser)
        repository.verifyCode(
                code = 1337,
                phone = mockPhone,
                onComplete = {},
                onError = {}
        ).test {
            val item = expectItem()
            assertEquals("TEST_TOKEN", item)
            expectComplete()
        }

        verify(userDAO, atLeastOnce()).nukeTableExceptById(1)

        verify(authService, atLeast(2)).sendUserCodeToVerify(mockCodeBody)
        verify(cartDAO, atLeast(2)).nukeTable()
        verify(productDAO, atLeast(2)).nukeTable()
        verify(userDAO, atLeast(2)).getUserById(1)
    }
}