package com.ponomar.shoper.viewmodel

import androidx.lifecycle.Observer
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
import com.ponomar.shoper.ui.auth.code3.AuthCodeViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthCodeViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: AuthCodeViewModel

    @Before
    fun setup(){
        repository = AuthRepository(client, daoHolder)
        viewModel = AuthCodeViewModel(repository)
    }

    @Test
    fun sendUserCodeToVerify():Unit = runBlocking {
        val mockPhone = "89967305022"
        val mockCodeBody = CodeBody(1337,mockPhone,"test")
        whenever(authService
                .sendUserCodeToVerify(mockCodeBody))
                .thenReturn(ApiResponse.of { Response.success(TokenResponse(30,"TEST_TOKEN",1)) })
        val observer: Observer<String> = mock()
        val fetchedData = repository.verifyCode(
                phone = mockPhone,
                code = 1337,
                firstName = "test",
                onComplete = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.sendUserCodeToVerify(
                phone = mockPhone,
                firstName = "test",
                code = 1337
        )

        verify(observer).onChanged("TEST_TOKEN")
        verify(authService, atLeastOnce()).sendUserCodeToVerify(mockCodeBody)
        fetchedData.removeObserver(observer)
    }
}