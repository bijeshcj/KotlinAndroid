package com.bijesh.coroutine.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bijesh.coroutine.repository.CoroutineRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.CancellationException

class CoroutineViewModel : ViewModel() {

    val repository: CoroutineRepository = CoroutineRepository()


    private var _asyncWithLazy = MutableLiveData<String>()
    var asyncWithLazy : LiveData<String> = _asyncWithLazy

    private var _structuredConcurrencyWithAsync = MutableLiveData<String>()
    var structuredConcurrencyWithAsync : LiveData<String> = _structuredConcurrencyWithAsync


    suspend fun structuredConcurrencyWithAsync(){
        viewModelScope.launch {
            val response1 = async {  repository.doSomethingUsefulOne() }
            val response2 = async { repository.doSomethingUsefulTwo() }
            _structuredConcurrencyWithAsync.value = "${response1.await()}  ${response2.await()}"
        }
    }

    suspend fun asyncWithLazy(){
        viewModelScope.launch {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUseful1() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUseful2() }
            one.start()
            two.start()
            val response = "${one.await().second}  ${two.await().second}"
            _asyncWithLazy.value = response
        }
    }

    suspend fun doSomethingUseful1() : Pair<Int,String>{
        delay(2000L)
        return Pair(200,"Hello")
    }

    suspend fun doSomethingUseful2() : Pair<Int,String>{
        delay(1000L)
        return Pair(201,"Success")
    }

    suspend fun timeoutWithPolling(){
        withTimeout(60000L){
            println("Start polling api")
            repeat(10){
                println("fetching api times $it")
                if(it == 5){
                    cancel(CancellationException("APi success"))
                }
                delay(10000L)
            }
        }
    }

    suspend fun anotherCoroutineTimeout(){
        try {
            withTimeout(5000L) {
                println("Started polling")
                repeat(10) {
                    println("fetching orders api")
                    delay(2000)
                }
                println("Task is completed")
            }
        }catch (e: TimeoutCancellationException){
            println(e.printStackTrace())
        }
    }

    suspend fun closingResourceInFinally(){
        coroutineScope {
            val job = launch {
                try {
                    repeat(100) {
                        println("I am sleeping $it")
                        delay(500L)
                    }
                }finally {
                    println("I am running finally")
                }
            }

            delay(1300)
            println("I am tired of waiting")
            job.cancelAndJoin()
            println("Now I can quit")
        }
    }

    suspend fun cancelCoroutine(){
        coroutineScope {
            val job = launch {
                repeat(10){ i->
                    println("I am sleeping $i")
                    delay(500L)
                }
            }

            delay(300L)
            println("I am tired of waiting")
            job.cancel()
            job.join()
            println("Now I can quit")
        }
    }

    suspend fun doNetworkCall(): String{
        delay(8000L)
        return "Hello"
    }

    suspend fun doNetworkCall2(): String{
        delay(3000L)
        return "World"
    }


    fun simpleFlow() : Flow<Int> = flow {
        for (i in 1..10){
            delay(1000)
            emit(i)
        }
    }

    suspend fun doWorld() = coroutineScope {
       launch {
           delay(1000L)
           println("World!")
       }
       println("Hello")
    }

    suspend fun doWorldJob() = coroutineScope {
        delay(3000L)
        val job = launch {
            delay(1000L)
            Log.d(TAG,"World")
        }
        Log.d(TAG,"Hello")
        job.join()
        Log.d(TAG,"Done")
    }



  companion object{
      private val TAG = "CoroutineViewModel"
  }

}