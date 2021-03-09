package com.ponomar.shoper.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockUser
import com.ponomar.shoper.model.UpdateEmailResponse
import com.ponomar.shoper.model.body.EmailBody
import com.ponomar.shoper.repository.AuthRepository
import com.ponomar.shoper.repository.UserRepository
import com.ponomar.shoper.ui.auth.email4.AuthEmailViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthEmailViewModelTest: ApiAbstractViewModel(){
    private lateinit var repository: UserRepository
    private lateinit var viewModel: AuthEmailViewModel

    @Before
    fun setup(){
        repository = UserRepository(client, daoHolder)
        viewModel = AuthEmailViewModel(repository)
    }

    @Test
    fun updateEmailOfUserTest():Unit = runBlocking {
        val mockUser = mockUser()
        val mockEmailBody = EmailBody(mockUser.email!!,"TEST_TOKEN")
        whenever(userService
                .updateUserInfo(mockEmailBody))
                .thenReturn(ApiResponse.of { Response.success(UpdateEmailResponse(0,mockUser.id)) })
        val observer: Observer<Int> = mock()
        val fetchedData = repository.updateUserEmail(
                email = mockUser.email!!,
                token = "TEST_TOKEN",
                onComplete = {},
                onError = {}
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.updateEmailOfUser("TEST_TOKEN",mockUser.email!!)
        delay(200)
        verify(observer).onChanged(0)
        verify(userService, atLeastOnce()).updateUserInfo(mockEmailBody)
        fetchedData.removeObserver(observer)
    }
}