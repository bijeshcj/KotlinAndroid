package com.bijesh.coroutine.rxjava

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxJavaViewModel(private val rxRepo: RxJavaRepository = RxJavaRepository()) : ViewModel() {

   private val _fromOperatorLiveData = MutableLiveData<Array<Int>>()
    val fromOperatorLiveData = _fromOperatorLiveData

    fun fromOperator() = rxRepo.fromOperator()

    fun fromOperatorWithState(){
        rxRepo.fromOperator().subscribe(object : Observer<Array<Int>>{
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

            override fun onNext(t: Array<Int>) {
                _fromOperatorLiveData.value = t
            }

        })
    }

}