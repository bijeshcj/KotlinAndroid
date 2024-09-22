package com.bijesh.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bijesh.coroutine.databinding.ActivityFlowMainBinding
import com.bijesh.coroutine.viewmodels.FlowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowMainActivity : AppCompatActivity() {

    val flowViewModel: FlowViewModel by viewModels<FlowViewModel>()
    private lateinit var binding: ActivityFlowMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_flow_main)

        binding = ActivityFlowMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFlow.setOnClickListener{

            lifecycleScope.launch {
                flowViewModel.flowWithOperator()
                flowViewModel.flowOperator.collect{
                    Log.d(TAG,"$it")
                }
            }


//            flowViewModel.withContextDemo()
//            lifecycleScope.launch {
//                    flowViewModel.withContextDemo.collect {
//                        log("In activity collect $it")
//                        handlePrint(binding.textViewFlow, it)
//                    }
//            }

//            GlobalScope.launch {
//                flowViewModel.getSequentialFlowResponse()
//                flowViewModel.sequentialFlowResponse.collect {
//                    Log.d(TAG,"$it")
//                }
//            }

//            GlobalScope.launch {
//                flowViewModel.ordersAccumalatedPrice()
//            }

//            GlobalScope.launch {
//                flowViewModel.reduceOperator()
//                flowViewModel.totalPrice.collect{ response ->
//                    Log.d(TAG,"Reduced $response")
//                }
//            }

//            GlobalScope.launch {
//                flowViewModel.simpleFlowTransform().collect{ response ->
//                    Log.d(TAG,"Response $response")
//                }
//            }
//            GlobalScope.launch {
//                flowViewModel.limitingFlow().collect{ item ->
//                    Log.d(TAG,"Response $item")
//                }
//            }
        }
    }

    fun log(message:String){
        print("${Thread.currentThread().name} $message")
    }

    fun handlePrint(textView: TextView,newResponse:String){
        var sb = StringBuilder(textView.text)
        sb.append("\n$newResponse")
        textView.text = sb.toString()
    }

    companion object{
        private const val TAG = "FlowMainActivity"
    }

}