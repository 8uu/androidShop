package com.ponomar.shoper.ui.news

import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.entities.News
import com.ponomar.shoper.repository.AnotherThingsRepository

class NewsViewModel @ViewModelInject constructor(
        private val repository:AnotherThingsRepository,
        @Assisted savedStateHandle: SavedStateHandle
): LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _fetchingNewsPage:MutableLiveData<Int> = MutableLiveData()


    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val newsLiveData:LiveData<List<News>>


    init {
        newsLiveData = _fetchingNewsPage.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.fetchNews(
                        onComplete = {isLoading.set(false)},
                        onError = {_toastMutableLiveData.value = it}
                ).asLiveData()
            }
        }
    }

    fun fetchNewsAt(page:Int = 0){
        _fetchingNewsPage.value = page
    }

}