package com.bijesh.coroutine.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bijesh.coroutine.R
import com.bijesh.coroutine.databinding.ActivityRxjavaMainBinding
import com.bijesh.coroutine.models.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Arrays

class RxJavaMainActivity : AppCompatActivity() {

    private var binding : ActivityRxjavaMainBinding? = null
    private val rxJavaViewModel : RxJavaViewModel by viewModels<RxJavaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRxjavaMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        with(binding){
            this?.btnRxJava2?.setOnClickListener{

                rxJavaViewModel.filterOperator()
                rxJavaViewModel.filterOperator.observe(this@RxJavaMainActivity){ response ->
                    Log.d(TAG,"$response")
                    tvRxJava2.text = "$response"
                }

//                filterOperator()

//                rxJavaViewModel.createOperator()
//                rxJavaViewModel.createOperator.observe(this@RxJavaMainActivity){response ->
//                    Log.d(TAG,"$response")
//                    tvRxJava2.text = "$response"
//                }

//                rxJavaViewModel.timerOperator()
//                rxJavaViewModel.timerOperator.observe(this@RxJavaMainActivity){ response ->
//                    Log.d(TAG,"$response")
//                    tvRxJava2.text = "$response"
//                }

//                rxJavaViewModel.fromIntervalOperator()
//                rxJavaViewModel.fromInterval.observe(this@RxJavaMainActivity){ response ->
//                    Log.d(TAG,"$response")
//                    tvRxJava2.text = "$response"
//                }

//                rxJavaViewModel.fromRangeOperator()
//                rxJavaViewModel.fromRangeOperator.observe(this@RxJavaMainActivity) { response ->
//                    Log.d(TAG, "$response")
//                    tvRxJava2.text = "$response"
//                }

//                rxJavaViewModel.fromIterable()
//                 rxJavaViewModel.fromIterbaleLiveData.observe(this@RxJavaMainActivity,{ response ->
//                     Log.d(TAG,"$response")
//                     tvRxJava2.text = response
//                 })

//                simpleRx()
//                simpleRx1()



//                fromOperator()
//                rxJavaViewModel.fromOperatorWithState()
//                rxJavaViewModel.fromOperatorLiveData.observe(this@RxJavaMainActivity){
//                    Log.d(TAG,"from live data ${it.contentToString()}")
//                }

            }
        }

    }

    val listOfUsers = mutableListOf(
        User(1,"Rohit",40),
        User(2,"Karthik",32),
        User(3,"Sam",30),
        User(4,"Peter",45),
        User(5,"Ramesh",25),
        User(6,"Suresh",18),
    )

    @SuppressLint("CheckResult")
    private fun filterOperator(){
       Observable.fromIterable(listOfUsers)
           .filter { it.age > 30 }
           .subscribe(
               { response ->
                   Log.d(TAG,"$response")
               },
               {
                   Log.e(TAG,it.toString())
               }
           )
    }


    private fun fromOperator(){
        rxJavaViewModel.fromOperator().subscribe(object : Observer<Array<Int>>{
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG,"onSubscribe")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG,"onError")
            }

            override fun onComplete() {
                Log.d(TAG,"onComplete")
            }

            override fun onNext(t: Array<Int>) {
                Log.d(TAG,"Next ${t.contentToString()}")
            }

        })
    }

    fun simpleRx1(){
        val observable = Observable.just(1,2,3,4,5)
        val observer = object : Observer<Int>{
            override fun onSubscribe(d: Disposable) {
                printLog("onSubscribe")
            }

            override fun onError(e: Throwable) {
                printLog("onError")
            }

            override fun onComplete() {
                printLog("onComplete")
            }

            override fun onNext(t: Int) {
                printLog("onSubscribe $t")
            }

        }
        observable.subscribe(observer)
    }

    @SuppressLint("CheckResult")
    fun simpleRx(){
        Observable.just("Chennai","London","Aberdeen","Glasgow","Dundee")
            .map {
                it.uppercase()
            }.flatMap {
                Observable.just(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::printLog,
            { throwable ->
                Log.d(TAG,"Error : ${throwable.message}")
            },
            {
                Log.d(TAG,"Completed")
            })
    }

    private fun printLog(str:String){
        Log.d(TAG,"City : $str")
    }

    companion object{
        private val TAG = "RxJavaMainActivity"
    }
}