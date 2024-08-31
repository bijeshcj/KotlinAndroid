package com.bijesh.coroutine.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {

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