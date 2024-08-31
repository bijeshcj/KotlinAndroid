package com.bijesh.coroutine.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bijesh.coroutine.repository.FlowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform

class FlowViewModel : ViewModel() {

    var flowRepository: FlowRepository = FlowRepository()

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