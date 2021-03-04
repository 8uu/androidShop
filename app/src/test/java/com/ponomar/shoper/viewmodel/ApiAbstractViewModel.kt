package com.ponomar.shoper.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ponomar.shoper.common.ApiAbstract
import com.ponomar.shoper.common.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class ApiAbstractViewModel: ApiAbstract() {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
}