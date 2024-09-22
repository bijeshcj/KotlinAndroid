package com.bijesh.coroutine.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bijesh.coroutine.repository.FlowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.skip
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowViewModel : ViewModel() {

    var flowRepository: FlowRepository = FlowRepository()

    val _totalPrice : MutableStateFlow<Double> = MutableStateFlow(0.0)
    val totalPrice: MutableStateFlow<Double> get() = _totalPrice

    private val _sequentialFlowResponse: MutableStateFlow<String> = MutableStateFlow("")
    val sequentialFlowResponse = _sequentialFlowResponse.asStateFlow()

    private val _withContextDemo : MutableStateFlow<String> = MutableStateFlow<String>("")
    val withContextDemo = _withContextDemo.asStateFlow()

    private val _flowOperator : MutableStateFlow<Int> = MutableStateFlow(1)
    val flowOperator = _flowOperator.asStateFlow()

    fun flowWithOperator(){
        viewModelScope.launch {
            flowRepository.flowWithOperator()
                .flatMapLatest {
                    flow {
                        delay(2000L)
                        emit(it * 100)
                    }
                }
                .collect{ counter ->
                _flowOperator.value = counter
            }
        }
    }


//    suspend fun reduceOperator(){
//        flowRepository.flowOfOrders().flatMapConcat { orders ->
//            orders.asFlow()
//        }.reduce { accumulator, value -> accumulator + value.price }
//    }
fun log(message:String){
    print("${Thread.currentThread().name} $message")
}

    fun withContextDemo(){
        viewModelScope.launch {
                flowRepository.withContextDemo().collect { status ->
                    log("In viewmodel collect $status")
                    _withContextDemo.value = status
                }
        }
    }

    suspend fun getSequentialFlowResponse(){
        flowRepository.sequentialFlow().collect{ response ->
            println("In collect block in viewmodel $response")
            _sequentialFlowResponse.value = response
        }
    }

    suspend fun reduceOperator(){
        flowRepository.priceList()
            .flatMapConcat { orders ->
                println(orders)
                orders.asFlow() }
            .reduce { accumulator, value ->
            println("acc $accumulator val $value")
            accumulator + value }.let { price ->
                println(price)
                _totalPrice.value = price}
    }

   suspend fun ordersAccumalatedPrice(){
       flowRepository.orderList().map { orders ->
           println(orders)
       }
   }

    fun limitingFlow() : Flow<Int> = flow {
        try {
            (1..10).forEach { i ->
                emit(i)
                delay(1000L)
            }
        }finally {
            Log.d("FlowViewModel","Limiting flow cancelled")
        }
    }.take(5)


    fun simpleFlow2(): Flow<String> = flow {
        for(i in 1..3){
            delay(1000L)
            emit(i)
        }
    }.map { request -> performRequest(request)
    }

    fun simpleFlowTransform() : Flow<String> = flow {
        for(i in 1..3){
            delay(1000L)
            emit(i)
        }
    }.transform { request ->
        emit("Making request $request")
        emit(performRequest(request))
    }

    fun simpleFlowAsFlow() : Flow<String> = flow {
        (1..3).asFlow().map {
            performRequest(it)
        }
    }

    suspend fun performRequest(v:Int): String{
        delay(1000L)
        return "response $v"
    }

    fun simpleFlow(): Flow<Int> = flow {
        for(i in 1..30){
            delay(1000)
            emit(i)
        }
    }

    fun simpleSequence() : Sequence<Int> = sequence {
        for(i in 1..3){
            Thread.sleep(1000)
            yield(i)
        }
    }


}