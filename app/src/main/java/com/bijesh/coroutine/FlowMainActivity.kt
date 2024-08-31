package com.bijesh.coroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bijesh.coroutine.databinding.ActivityFlowMainBinding
import com.bijesh.coroutine.viewmodels.FlowViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class FlowMainActivity : AppCompatActivity() {

    val flowViewModel: FlowViewModel by viewModels<FlowViewModel>()
    private lateinit var binding: ActivityFlowMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_flow_main)

        binding = ActivityFlowMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener{
//            GlobalScope.launch {
//                flowViewModel.simpleFlowTransform().collect{ response ->
//                    Log.d(TAG,"Response $response")
//                }
//            }
            GlobalScope.launch {
                flowViewModel.limitingFlow().collect{ item ->
                    Log.d(TAG,"Response $item")
                }
            }
        }


    }

    companion object{
        private const val TAG = "FlowMainActivity"
    }

}