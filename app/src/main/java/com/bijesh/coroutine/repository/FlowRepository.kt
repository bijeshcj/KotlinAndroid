package com.bijesh.coroutine.repository

import com.bijesh.coroutine.models.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FlowRepository {

    var counter = 1


    fun flowOfPersonNames(): Flow<String>{
        return flowOf("rey","emd","alex","mari","peter")
    }

    fun flowWithOperator() = flow {
        while(counter <= 10) {
            emit(counter++)
            delay(1000L)
        }
    }

    fun withContextDemo() = flow<String> {
        emit("Loading")
        log("Loading")
            delay(2000)
            emit("response")
            log("response")
            delay(1000)
            emit("completed")
            log("completed")
    }.flowOn(Dispatchers.IO)

    private fun log(message:String){
        print("${Thread.currentThread().name} $message")
    }

    fun sequentialFlow(): Flow<String>{
        return (1..5).asFlow().filter {
            println("Filter block $it")
            it % 2 == 0
        }.map {
            println("Map block $it")
            "Response $it"
        }
    }

    fun flowOfOrders() : Flow<List<Order>> {
        return flowOf(
            listOf(Order("Snicker",100.00),
                Order("Kitkat",200.00),
                Order("Oreo",300.00))
        )
    }

    fun priceList(): Flow<List<Double>>{
        return flowOf(listOf(100.00,200.00,300.00))
    }

    fun orderList(): Flow<List<Order>>{
        return flowOf(
            listOf(Order("Snicker",100.00),
                Order("Kitkat",200.00),
                Order("Oreo",300.00))
        )
    }

    fun simpleFlow(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }




}