package com.ponomar.shoper.ui.cart

import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.SQLoutput.CartInnerProduct
import com.ponomar.shoper.repository.MainRepository

class CartViewModel @ViewModelInject constructor(
        private val repository: MainRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {

    private val _toastMutable:MutableLiveData<String> = MutableLiveData()



    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val toastLiveData:LiveData<String> = _toastMutable
    lateinit var cartLiveData: LiveData<List<CartInnerProduct>>


    //TODO:FIX EQUALS
    fun fetchCartData(){
        cartLiveData = launchOnViewModelScope {
            isLoading.set(true)
            repository.fetchCart(
                    onComplete = {isLoading.set(false)},
                    onError = {_toastMutable.value = it}
            ).asLiveData()
        }
    }
}