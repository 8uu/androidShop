package com.ponomar.shoper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockUser
import com.ponomar.shoper.model.UserResponse
import com.ponomar.shoper.model.body.TokenBody
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.repository.UserRepository
import com.ponomar.shoper.ui.profile.ProfileViewModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ProfileViewModelTest: ApiAbstractViewModel() {
    private lateinit var repository: UserRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup(){
        repository = UserRepository(client, daoHolder)
        viewModel = ProfileViewModel(repository, SavedStateHandle())
    }

    @Test
    fun fetchUserInfoFromDaoTest():Unit = runBlocking {
        val mockUser = mockUser()
        whenever(userDAO.getUser()).thenReturn(mockUser)
        val observer: Observer<User> = mock()
        val fetchedData: LiveData<User> = repository.fetchUserInfo(
                "TEST_TOKEN",
                onComplete = {},
                onError = {},
                isForceUpdate = false
        ).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.updateUserInfo("TEST_TOKEN")
        verify(observer).onChanged(mockUser)
        verify(userDAO, atLeastOnce()).getUser()
    }

    @Test
    fun fetchUserInfoFromNetworkTest():Unit = runBlocking {
        val mockUser = mockUser()
        val mockTokenBody = TokenBody("TEST_TOKEN")
        whenever(userService.fetchUserInfo(mockTokenBody)).thenReturn(ApiResponse.of { Response.success(UserResponse(0, mockUser)) })
        val observer: Observer<User> = mock()
        val fetchedData: LiveData<User> = repository.fetchUserInfo(
                "TEST_TOKEN",onComplete = {}, onError = {},false).asLiveData()
        fetchedData.observeForever(observer)
        viewModel.updateUserInfo("TEST_TOKEN")
        verify(observer).onChanged(mockUser)
        verify(userService, atLeastOnce()).fetchUserInfo(TokenBody("TEST_TOKEN"))
    }
}