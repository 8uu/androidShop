package com.ponomar.shoper.ui.cart

import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct
import com.ponomar.shoper.repository.MainRepository

class CartViewModel @ViewModelInject constructor(
        private val repository: MainRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {

    private val _toastMutable:MutableLiveData<String> = MutableLiveData()
    private val _fetchingIncStatus:MutableLiveData<Int> = MutableLiveData()
    private val _fetchingDecStatus:MutableLiveData<Int> = MutableLiveData()

    val decStatusLiveData:LiveData<Int>
    val incStatusLiveData:LiveData<Int>
    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val toastLiveData:LiveData<String> = _toastMutable
    lateinit var embeddedLiveData: LiveData<List<EmbeddedProduct>>

    init {
        incStatusLiveData = _fetchingIncStatus.switchMap {
            isLoading.set(true)
            launchOnViewModelScope {
                repository.incQuantityOfItemClick(
                        it,
                        onComplete = {isLoading.set(false)}
                ).asLiveData()
            }
        }

            decStatusLiveData = _fetchingDecStatus.switchMap {
                isLoading.set(true)
                launchOnViewModelScope {
                    repository.decQuantityOfItemClick(
                            it,
                            onComplete = {isLoading.set(false)}
                    ).asLiveData()
                }
            }

        embeddedLiveData = launchOnViewModelScope {
            isLoading.set(true)
            val data = repository.fetchCart(
                    onComplete = {isLoading.set(false)},
                    onError = {_toastMutable.value = it}
            )
            data.asLiveData()
        }
    }


    //TODO:FIX EQUALS
//    fun fetchCartData(){
//        embeddedLiveData = launchOnViewModelScope {
//            isLoading.set(true)
//            val data = repository.fetchCart(
//                    onComplete = {isLoading.set(false)},
//                    onError = {_toastMutable.value = it}
//            )
//            data.asLiveData()
//        }
//    }

    fun onMinusItemClick(pid:Int){
        _fetchingDecStatus.value = pid
    }

    fun onPlusItemClick(pid:Int){
        _fetchingIncStatus.value = pid
    }
}