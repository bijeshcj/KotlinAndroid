package com.bijesh.coroutine

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bijesh.coroutine.databinding.ActivityCoroutineMainBinding
import com.bijesh.coroutine.viewmodels.CoroutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log
import kotlin.time.measureTime

class CoroutineMainActivity :  AppCompatActivity() {

    val coroutineViewModel: CoroutineViewModel by viewModels()
    lateinit var binding: ActivityCoroutineMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//        GlobalScope.launch {
////            coroutineViewModel.doWorld()
////            coroutineViewModel.doWorldJob()
//            coroutineViewModel.cancelCoroutine()
//        }

        coroutineViewModel.structuredConcurrencyWithAsync.observe(this@CoroutineMainActivity){
            println(it)
            binding.textView2.text = it
        }

        coroutineViewModel.asyncWithLazy.observe(this@CoroutineMainActivity) { response ->
            println(response)
            binding.textView2.text = response
        }

        binding.btnCoroutine?.setOnClickListener {
//            GlobalScope.launch {
//                coroutineViewModel.timeoutWithPolling()
//                coroutineViewModel.anotherCoroutineTimeout()
//                val timeTaken = measureTime {
//                    val result1 = async { coroutineViewModel.doSomethingUseful1() }
//                    val result2 = async { coroutineViewModel.doSomethingUseful2() }
//                    println(result1.await().second + result2.await().second)
//                }
//                println(timeTaken)

//                coroutineViewModel.asyncWithLazy()
//                coroutineViewModel.structuredConcurrencyWithAsync()

//            }

          startUnConfinedExample()

//        binding?.btnCoroutine?.setOnClickListener{
//            GlobalScope.launch {
//                coroutineViewModel.cancelCoroutine()
//            }
//        }


        }
    }


    private fun startUnConfinedExample(){
        CoroutineScope(Dispatchers.Unconfined).launch{
            logThread("Started in unconfined")

            withContext(Dispatchers.IO){
                delay(5000)
                logThread("Switching to main")
                binding.textView2.text = "Update in main thread"
            }
            logThread("Back to unconfined")
        }
    }

    private fun logThread(message:String){
        println("$message : ${Thread.currentThread().name}")

    }
}