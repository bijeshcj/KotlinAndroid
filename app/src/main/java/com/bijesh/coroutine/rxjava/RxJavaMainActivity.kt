package com.bijesh.coroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bijesh.coroutine.R
import com.bijesh.coroutine.databinding.ActivityRxjavaMainBinding
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.withContext

class RxJavaMainActivity : AppCompatActivity() {

    private var binding : ActivityRxjavaMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRxjavaMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        with(binding){
            this?.btnRxJava2?.setOnClickListener{
                simpleRx()
            }
        }

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