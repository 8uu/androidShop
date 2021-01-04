package com.ponomar.shoper.ui.auth.login

import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.CodeResponse
import com.ponomar.shoper.model.StatusResponse
import com.ponomar.shoper.repository.MainRepository

class LoginViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _phoneMutableLiveData:MutableLiveData<String> = MutableLiveData()
    val toastLiveData:LiveData<String> = _toastMutableLiveData
    val isLoading = ObservableBoolean(false)
    val codeLiveData:LiveData<Int>


    init {
        codeLiveData = _phoneMutableLiveData.switchMap {
            isLoading.set(true)
            launchOnViewModelScope {
                repository.sendUserDataToGenerateAuthCode(
                    it,
                    onSuccess = {isLoading.set(false)},
                    onError =  {_toastMutableLiveData.value = it}
                ).asLiveData()
            }
        }
    }



    fun sendUserPhone(phone:String){
        _phoneMutableLiveData.value = phone
    }
}