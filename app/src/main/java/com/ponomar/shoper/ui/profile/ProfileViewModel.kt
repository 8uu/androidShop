package com.ponomar.shoper.ui.profile

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.repository.MainRepository

class ProfileViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
):LiveCoroutinesViewModel() {

    private val mutableUserData:MutableLiveData<User> = MutableLiveData()
    private val mutableToastLiveData:MutableLiveData<String> = MutableLiveData()

    val isLoading: ObservableBoolean = ObservableBoolean(false)
    val toastLiveData:LiveData<String> = mutableToastLiveData
    val userData: LiveData<User> = mutableUserData

        init {
            mutableUserData.switchMap {
                isLoading.set(true)
                launchOnViewModelScope {
                    repository.fetchUserInfo(
                        onSuccess = { isLoading.set(false) },
                        onError = { mutableToastLiveData.value = "ERROR" }
                    ).asLiveData()
                }
            }
        }
}