package com.ponomar.shoper.ui.order

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.repository.AnotherThingsRepository
import com.ponomar.shoper.repository.OrderRepository

class OrderViewModel @ViewModelInject constructor(
    private val repository:OrderRepository,
    private val addressRepository: AnotherThingsRepository
) : LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _orderRequestLiveData:MutableLiveData<Pair<String,Address>> = MutableLiveData()

    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val isLoadingAddresses:ObservableBoolean = ObservableBoolean(false)
    val toastLiveData: LiveData<String> = _toastMutableLiveData
    val statusResponseLiveData:LiveData<Int>
    val addressesLiveData:LiveData<List<Address>>


    init {
        statusResponseLiveData = _orderRequestLiveData.switchMap {
            launchOnViewModelScope {
                repository.makeOrderRequest(
                    it.first,
                    it.second,
                    {isLoading.set(false)},
                    {_toastMutableLiveData.value = it}
                ).asLiveData()
            }
        }

        addressesLiveData = launchOnViewModelScope {
            isLoadingAddresses.set(true)
            addressRepository.fetchAddresses(
                    onComplete = {isLoadingAddresses.set(false)},
                    onError = {_toastMutableLiveData.value = it}
            ).asLiveData()
        }
    }

    fun makeOrderRequest(district:String,street:String,house:String,flat:Int,token:String){
        _orderRequestLiveData.value = Pair(token, Address(district,street,house,flat))
    }

}