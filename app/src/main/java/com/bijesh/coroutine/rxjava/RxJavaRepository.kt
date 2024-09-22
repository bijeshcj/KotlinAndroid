package com.bijesh.coroutine.rxjava

import io.reactivex.Observable
import io.reactivex.Observer

class RxJavaRepository {

    fun fromOperator() : Observable<Array<Int>> {
        return  Observable.fromArray(arrayOf(1,2,3,4,5))
    }

}