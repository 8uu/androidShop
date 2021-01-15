package com.ponomar.shoper.ui.order_detail


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ponomar.shoper.base.LiveCoroutinesViewModel

class OrderDetailViewModel : LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()

    val toastLiveData:LiveData<String> = _toastMutableLiveData
    val isLoading:ObservableBoolean = ObservableBoolean(false)
}