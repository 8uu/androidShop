package com.ponomar.shoper.ui.auth.code3

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.repository.MainRepository
import java.util.*
import kotlin.collections.HashMap

class AuthCodeViewModel @ViewModelInject constructor(
        private val repository: MainRepository
):LiveCoroutinesViewModel() {

    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _fetchingLiveDataUserData:MutableLiveData<HashMap<String, Any>> = MutableLiveData()

    val toastLiveData: LiveData<String> = _toastMutableLiveData
    val tokenLiveData:LiveData<String>


    val isLoading:ObservableBoolean = ObservableBoolean(false)

    init {
        tokenLiveData = _fetchingLiveDataUserData.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.verifyCode(
                        phone = it["phone"] as String,
                        firstName = it["firstName"] as String,
                        code = it["code"] as Int,
                        onSuccess = {
                            isLoading.set(false)
                                    },
                        onError = { _toastMutableLiveData.value = it }
                ).asLiveData()
            }
        }

    }

    fun sendUserPhoneToGenerateCode(phone:String,firstName:String,code:Int){
        val container:HashMap<String,Any> = hashMapOf(Pair("phone",phone),Pair("firstName",firstName),Pair("code",code))
        _fetchingLiveDataUserData.value = container
    }


}