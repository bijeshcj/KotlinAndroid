package com.bijesh.coroutine.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bijesh.coroutine.ui.view.recycler_view_adapter.model.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StickyHeaderViewModel : ViewModel() {

    private val _itemsMutableLiveData : MutableLiveData<List<Item>> = MutableLiveData()
    val items: LiveData<List<Item>> get() = _itemsMutableLiveData

    private val _networkState : MutableLiveData<NetworkState> = MutableLiveData()
    val networkState: LiveData<NetworkState> get() = _networkState

    init {
        fetchData()
    }

    fun fetchData(){
        _networkState.value = NetworkState.LOADING

        viewModelScope.launch {
            delay(2000L)
            val isSuccess = true
            if(isSuccess){
                _networkState.value = NetworkState.SUCCESS
            }else{
                _networkState.value = NetworkState.FAILURE
            }
        }

    }

}

enum class NetworkState{
    LOADING,
    SUCCESS,
    FAILURE,
    IDLE
}