package com.ponomar.shoper.ui.auth.phone2

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.repository.AuthRepository


class AuthPhoneViewModel @ViewModelInject constructor(
        private val repository: AuthRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
):LiveCoroutinesViewModel() {
    private val _toastMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private lateinit var _fetchingLiveDataPhone: MutableLiveData<String>

    val toastLiveData: LiveData<String> = _toastMutableLiveData
    lateinit var codeLiveData: LiveData<Int>


    val isLoading: ObservableBoolean = ObservableBoolean(false)



    fun initLiveData(){
        _fetchingLiveDataPhone = MutableLiveData() //TODO:Костыль
        codeLiveData = _fetchingLiveDataPhone.switchMap {
            Log.e("code","launch")
            launchOnViewModelScope {
                isLoading.set(true)
                repository.sendUserDataToGenerateAuthCode(
                        it,
                        onSuccess = {
                            isLoading.set(false)
                            codeLiveData = MutableLiveData()
                        },
                        onError = {
                            _toastMutableLiveData.value = it
                        }
                ).asLiveData()
            }
        }
    }

    fun sendUserPhoneToGenerateCode(phone:String){
        _fetchingLiveDataPhone.value = phone
    }
}