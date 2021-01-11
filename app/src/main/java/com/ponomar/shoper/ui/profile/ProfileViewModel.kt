package com.ponomar.shoper.ui.profile

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
):LiveCoroutinesViewModel() {

    private val mutableToastLiveData:MutableLiveData<String> = MutableLiveData()
    private val mutableTokenLiveData:MutableLiveData<String> = MutableLiveData()
    private var isForceUpdateNeed = false

    val isLoading: ObservableBoolean = ObservableBoolean(false)
    val toastLiveData:LiveData<String> = mutableToastLiveData
    val userData: LiveData<User>

        init {

            userData = mutableTokenLiveData.switchMap {
                launchOnViewModelScope {
                    isLoading.set(true)
                    repository.fetchUserInfo(
                            it,
                            onComplete = {
                                isLoading.set(false)
                                isForceUpdateNeed = false
                                         },
                            onError = { mutableToastLiveData.value = it },
                            isForceUpdate = isForceUpdateNeed
                    ).asLiveData()
                }
            }

        }

    fun updateUserInfo(token:String){
        mutableTokenLiveData.value = token
    }

    fun forceUpdateUserInfo(token: String){
        isForceUpdateNeed = true
        mutableTokenLiveData.value = token
    }

}