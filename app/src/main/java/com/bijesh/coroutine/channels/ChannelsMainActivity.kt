package com.bijesh.coroutine.channels

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bijesh.coroutine.R
import com.bijesh.coroutine.databinding.ActivityChannelMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChannelsMainActivity : AppCompatActivity() {

    private val messageChannel: Channel<String> = Channel()
    private var binding: ActivityChannelMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        setContentView(R.layout.activity_channel_main)

        val channelsViewModel by viewModels<ChannelsViewModel>()

        lifecycleScope.launch {
            channelsViewModel.messageFlow.collect{ message ->
                binding!!.button3.text = message
            }
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            sendMessages()
//        }
//
//        CoroutineScope(Dispatchers.Main).launch {
//            messageChannel.consumeEach { message ->
//                binding!!.button3.text = message
//            }
//        }

    }

    private suspend fun sendMessages(){
        val list = listOf("Chennai","London","Aberdeen","Glasgow","Dundee")
        for (message in list){
            delay(1000L)
            messageChannel.send(message)
        }
    }

}