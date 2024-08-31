package com.bijesh.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {



    fun launchCoroutine(){
        (1..100).forEach {
            viewModelScope.launch {
                val threadName = Thread.currentThread().name
                println("$it printed on ${threadName}")
            }
        }
    }

}