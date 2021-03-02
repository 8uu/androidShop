package com.ponomar.shoper.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ponomar.shoper.common.MockUtilUnit.mockUser
import com.ponomar.shoper.model.UpdateEmailResponse
import com.ponomar.shoper.model.UserResponse
import com.ponomar.shoper.model.body.EmailBody
import com.ponomar.shoper.model.body.TokenBody
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.Response
import kotlin.time.ExperimentalTime

class UserRepositoryTest: ApiAbstract() {
    private lateinit var repository: UserRepository

    @Before
    fun initRepository(){
        repository = UserRepository(client,daoHolder)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchUserInfoFromNetworkTest() = runBlocking{
        val mockUserResponse = UserResponse(0,mockUser())

        whenever(userDAO.getUser()).thenReturn(null)
        whenever(userService.fetchUserInfo(TokenBody("TEST_TOKEN"))).thenReturn(ApiResponse.of { Response.success(mockUserResponse) })

        repository.fetchUserInfo(
                "TEST_TOKEN",
                onComplete = {},
                onError = {},
                isForceUpdate = false
        ).test{
            val item = expectItem()
            assertEquals(1,item.id)
            assertEquals("User",item.firstName)
            expectComplete()
        }

        verify(userDAO, atLeastOnce()).nukeTable()
        verify(userDAO, atLeastOnce()).insert(mockUserResponse.data!!)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun fetchUserInfoFromDbTest(): Unit = runBlocking{
        val mockUser = mockUser()
        whenever(userDAO.getUser()).thenReturn(mockUser)

        repository.fetchUserInfo(
                "TEST_TOKEN",
                onComplete = {},
                onError = {}
        ).test {
            val item = expectItem()
            assertEquals(1,item.id)
            assertEquals("User",item.firstName)
            expectComplete()
        }
        verify(userDAO, atLeastOnce()).getUser()
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun updateUserEmailTest():Unit = runBlocking{
        val emailBody = EmailBody("test","TEST_TOKEN")
        whenever(userService.updateUserInfo(emailBody))
                .thenReturn(ApiResponse.of { Response.success(UpdateEmailResponse(0,null)) })

        repository.updateUserEmail("test","TEST_TOKEN",onComplete = {},onError = {})
                .test {
                    assertEquals(0,expectItem())
                    expectComplete()
                }
        verify(userService, atLeastOnce()).updateUserInfo(emailBody)
    }
}