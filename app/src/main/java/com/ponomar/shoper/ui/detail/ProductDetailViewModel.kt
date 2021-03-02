package com.ponomar.shoper.ui.detail

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.repository.CartRepository

class ProductDetailViewModel @ViewModelInject constructor(
    private val repository: CartRepository
    ): LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _decFetchMutableLiveData:MutableLiveData<Int> = MutableLiveData()
    private val _incFetchMutableLiveData:MutableLiveData<Int> = MutableLiveData()

    val toastLiveData: LiveData<String> = _toastMutableLiveData
    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val decStatusLiveData:LiveData<Int>
    val incStatusLiveData:LiveData<Int>

    init {
        decStatusLiveData = _decFetchMutableLiveData.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.decQuantityOfItemClick(
                        it,
                        onComplete = {isLoading.set(false)}
                ).asLiveData()
            }
        }

        incStatusLiveData = _incFetchMutableLiveData.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.incQuantityOfItemClick(
                        it,
                        onComplete = {isLoading.set(false)}
                ).asLiveData()
            }
        }
    }

    fun plusQuantityInCart(pid:Int){
        _incFetchMutableLiveData.value = pid
    }

    fun minusQuantityInCart(pid:Int){
        _decFetchMutableLiveData.value = pid
    }



}