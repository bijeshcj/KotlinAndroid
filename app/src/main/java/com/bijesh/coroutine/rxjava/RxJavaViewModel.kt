package com.bijesh.coroutine.rxjava

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijesh.coroutine.models.User
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RxJavaViewModel(private val rxRepo: RxJavaRepository = RxJavaRepository()) : ViewModel() {



   private val _fromOperatorLiveData = MutableLiveData<Array<Int>>()
    val fromOperatorLiveData = _fromOperatorLiveData

    private val _fromIterableCities = MutableLiveData<String>()
    val fromIterbaleLiveData = _fromIterableCities

    private val _fromRangeOperator = MutableLiveData<Int>()
    val fromRangeOperator = _fromRangeOperator

    private val _fromError = MutableLiveData<Throwable>()
    val fromError = _fromError

    private val _fromInterval = MutableLiveData<Long>()
    val fromInterval = _fromInterval

    private val _timerOperator = MutableLiveData<Long>()
    val timerOperator = _timerOperator

    private val _createOperator = MutableLiveData<String>()
    val createOperator = _createOperator

    private val _createOperatorflow = MutableStateFlow<String>("")
    val createOperatorflow = _createOperatorflow.asStateFlow()


    private val _filterOperator = MutableLiveData<User>()
    val filterOperator = _filterOperator

    @SuppressLint("CheckResult")
    fun filterOperator(){
        rxRepo.filterOperator()
            .filter { user -> user.age > 30 }
            .subscribe(
                { response ->
                    Log.d(TAG,"In RxJavaViewModel filterOperator function $response")
                    _filterOperator.postValue(response)
                },
                {
                    _fromError.postValue(it)
                }
            )
    }


    @SuppressLint("CheckResult")
    fun createOperator(){
        rxRepo.createOperator().subscribe(
            { response ->
                Log.d(TAG,"In RxJavaViewModel createOperator function $response")
                _createOperator.postValue(response)
                _createOperatorflow.value = response
            },
            {throwable ->
                _fromError.postValue(throwable)
            }
        )
    }


    @SuppressLint("CheckResult")
    fun timerOperator(){
        rxRepo.timerOperator().subscribe(
            {value ->
                _timerOperator.postValue(value)
            },{ throwable ->
                _fromError.postValue(throwable)
            }
        )
    }


    @SuppressLint("CheckResult")
    fun fromIntervalOperator(){
        rxRepo.fromInterval().subscribe({ value ->
            _fromInterval.postValue(value)
        },{ throwable ->
            _fromError.postValue(throwable)
        },{
            Log.d(TAG,"onCompleted...")
        })
    }


    @SuppressLint("CheckResult")
    fun fromRangeOperator(){
        rxRepo.fromRangeOperator().repeat(2).
        observeOn(Schedulers.io()).
        subscribeOn(AndroidSchedulers.mainThread()).subscribe({ value ->
            Log.d(TAG,"$value")
            _fromRangeOperator.postValue(value)
        },{ throwable ->
            Log.e(TAG,"Error while processing ")
        },{
            Log.d(TAG,"onCompleted...")
        })
    }


    fun fromIterable(){
        rxRepo.fromIterable().subscribe(object : Observer<String>{
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

            override fun onNext(t: String) {
                Log.d("RxJavaViewModel","$t")
                _fromIterableCities.postValue(t)
            }

        })
    }




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


    companion object{
        const val TAG = "RxJavaViewModel"
    }
}