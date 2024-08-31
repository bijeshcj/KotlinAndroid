package com.bijesh.coroutine.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChannelsViewModel : ViewModel() {

    private val _messageChannel =  Channel<String>()
    val messageFlow = _messageChannel.receiveAsFlow()

    init {
        sendMessages()
    }

    private fun sendMessages(){
        viewModelScope.launch {
            val list = listOf("Chennai","London","Aberdeen","Glasgow","Dundee")
            for (message in list){
                delay(1000L)
                _messageChannel.send(message)
            }
            _messageChannel.close()
        }
    }

}