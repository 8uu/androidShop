package com.ponomar.shoper.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.model.CodeResponse
import com.ponomar.shoper.model.TokenResponse
import com.ponomar.shoper.model.body.CodeBody
import com.ponomar.shoper.model.body.PhoneBody
import com.ponomar.shoper.repository.AuthRepository
import com.ponomar.shoper.ui.auth.login.LoginViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: LoginViewModel
    private val mockPhone: String = "8(996)-730-50-22"
    private val mockPhoneBody = PhoneBody(mockPhone)

    @Before
    fun setup(){
        repository = AuthRepository(client, daoHolder)
        viewModel = LoginViewModel(repository, SavedStateHandle())
    }

    @Test
    fun sendUserPhoneTest():Unit = runBlocking {
        whenever(authService
                .sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody))
                .thenReturn(ApiResponse.of { Response.success(CodeResponse(0,1337)) } )
        val observer: Observer<Int> = mock()
        val fetchedData = repository.sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
                phone = mockPhone,
                onComplete = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.sendUserPhone(mockPhone)
        delay(200)

        verify(observer).onChanged(1337)
        verify(authService, atLeastOnce()).sendUserDataToGenerateCodeWhenUserTryToLogin(mockPhoneBody)
        fetchedData.removeObserver(observer)
    }

    @Test
    fun sendUserCodeTest():Unit = runBlocking {
        val mockCodeBody = CodeBody(1337,mockPhone,null)
        whenever(authService
                .sendUserCodeToVerify(mockCodeBody))
                .thenReturn(ApiResponse.of { Response.success(TokenResponse(30,"TEST_TOKEN",1)) })
        val observer: Observer<String> = mock()
        val fetchedData = repository.verifyCode(
                phone = mockPhone,
                firstName = null,
                code = 1337,
                onComplete = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.sendUserCode(1337)
        delay(500)

        verify(observer).onChanged("TEST_TOKEN")
        verify(authService, atLeastOnce()).sendUserCodeToVerify(mockCodeBody)
        fetchedData.removeObserver(observer)
    }
}