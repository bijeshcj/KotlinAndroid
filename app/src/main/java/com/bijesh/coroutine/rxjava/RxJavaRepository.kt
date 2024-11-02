package com.bijesh.coroutine.rxjava

import com.bijesh.coroutine.models.User
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import java.util.concurrent.TimeUnit

class RxJavaRepository {

    val listOfCities = mutableListOf("Chennai","London","Aberdeen","Glasgow","Dundee")
    val listOfUsers = mutableListOf(
        User(1,"Rohit",40),
        User(2,"Karthik",32),
        User(3,"Sam",30),
        User(4,"Peter",45),
        User(5,"Ramesh",25),
        User(6,"Suresh",18),
    )

    fun filterOperator(): Observable<User>{
        return Observable.fromIterable(listOfUsers)
    }


    fun createOperator(): Observable<String>{
//        return Observable.create { emitter ->
//            for (city in listOfCities) {
//                Thread.sleep(30000)
//                emitter.onNext(city)
//            }
//        }
        return Observable.create(ObservableOnSubscribe { emitter ->
            try {
                for(city in listOfCities){
                    emitter.onNext(city)
                    Thread.sleep(1000)
                }
                emitter.onComplete()
            }catch (e:Exception){
                emitter.onError(e)
            }
        })
    }


    fun timerOperator(): Observable<Long>{
        return Observable.timer(5,TimeUnit.SECONDS)
    }

    fun fromInterval(): Observable<Long>{
        return Observable.interval(5,1, TimeUnit.SECONDS).takeWhile{ value ->
            value <= 10
        }
    }

    fun fromRangeOperator(): Observable<Int>{
        Thread.sleep(2000)
        return Observable.range(1,10)
    }

    fun fromIterable(): Observable<String>{
        return Observable.fromIterable(listOfCities)
    }

    fun fromOperator() : Observable<Array<Int>> {
        return  Observable.fromArray(arrayOf(1,2,3,4,5))
    }



}