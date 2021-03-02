package com.ponomar.shoper.ui.order_detail


import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.repository.OrderRepository

class OrderDetailViewModel @ViewModelInject constructor(
        private val repository: OrderRepository
) : LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()

    val toastLiveData:LiveData<String> = _toastMutableLiveData
    val isLoading:ObservableBoolean = ObservableBoolean(false)
}