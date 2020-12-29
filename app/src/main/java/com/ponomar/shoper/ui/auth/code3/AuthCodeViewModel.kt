package com.ponomar.shoper.ui.auth.code3

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.repository.MainRepository

class AuthCodeViewModel @ViewModelInject constructor(
        private val repository: MainRepository
):LiveCoroutinesViewModel() {

    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _fetchingLiveDataPhone:MutableLiveData<String> = MutableLiveData()

    val toastLiveData: LiveData<String> = _toastMutableLiveData
    val codeLiveData:LiveData<Int>


    val isLoading:ObservableBoolean = ObservableBoolean(false)

    init{
        codeLiveData = _fetchingLiveDataPhone.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.sendUserDataToGenerateAuthCode(
                        it,
                        onSuccess = {isLoading.set(false)},
                        onError = {_toastMutableLiveData.value = it}
                ).asLiveData()
            }
        }


    }

    fun sendUserPhoneToGenerateCode(phone:String){
        _fetchingLiveDataPhone.value = phone
    }


}