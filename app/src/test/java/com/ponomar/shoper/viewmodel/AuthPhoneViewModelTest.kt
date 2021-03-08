package com.ponomar.shoper.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.model.CodeResponse
import com.ponomar.shoper.model.body.PhoneBody
import com.ponomar.shoper.repository.AuthRepository
import com.ponomar.shoper.ui.auth.phone2.AuthPhoneViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthPhoneViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: AuthPhoneViewModel

    @Before
    fun setup(){
        repository = AuthRepository(client, daoHolder)
        viewModel = AuthPhoneViewModel(repository, SavedStateHandle())
        viewModel.initLiveData()
    }

    @Test
    fun sendUserPhoneToGenerateCode():Unit = runBlocking {
        val mockPhone = "89967305022"
        val mockPhoneBody = PhoneBody(mockPhone)
        whenever(authService
                .sendUserDataToGenerateCode(mockPhoneBody))
                .thenReturn(ApiResponse.of { Response.success(CodeResponse(0,1337)) })
        val observer: Observer<Int> = mock()
        val fetchedData = repository.sendUserDataToGenerateAuthCode(
                phone = mockPhone,
                onSuccess = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.sendUserPhoneToGenerateCode(mockPhone)

        verify(observer).onChanged(1337)
        verify(authService, atLeastOnce()).sendUserDataToGenerateCode(mockPhoneBody)
    }
}