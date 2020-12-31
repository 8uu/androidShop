package com.ponomar.shoper.ui.cart

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private val _toastMutable:MutableLiveData<String> = MutableLiveData()
    private val _fetchToken:MutableLiveData<String> = MutableLiveData()


    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val toastLiveData:LiveData<String> = _toastMutable



    fun fetchCartData(token:String){
        _fetchToken.value = token
    }
}