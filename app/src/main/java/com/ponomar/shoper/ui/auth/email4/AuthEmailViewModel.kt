package com.ponomar.shoper.ui.auth.email4

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ponomar.shoper.base.LiveCoroutinesViewModel

class AuthEmailViewModel @ViewModelInject constructor(
        private val repository: MainRepository
):LiveCoroutinesViewModel(){
    private lateinit var email:String
    private lateinit var token:String

    private val _mutableToastData: MutableLiveData<String> = MutableLiveData()
    private val _mutableEmailData:MutableLiveData<String> = MutableLiveData()

    val toastLiveData:LiveData<String> = _mutableToastData
    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val statusLiveData: LiveData<Int>

    init {
        statusLiveData = _mutableEmailData.switchMap {
            isLoading.set(true)
            launchOnViewModelScope {
                repository.updateUserEmail(
                        email,
                        token,
                        onComplete = {isLoading.set(false)},
                        onError = {_mutableToastData.value = it}
                ).asLiveData()
            }
        }
    }

    fun updateEmailOfUser(_token:String,_email:String){
        email = _email
        token = _token
        _mutableEmailData.value = email
    }

}