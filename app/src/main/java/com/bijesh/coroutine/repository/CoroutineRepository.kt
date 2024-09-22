package com.bijesh.coroutine.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class CoroutineRepository {

    val fakeDataRepository = FakeDataRepository()

    suspend fun doSomethingUsefulOne(): String{
        return fakeDataRepository.fetchDataFromNetwork()
    }

    suspend fun doSomethingUsefulTwo(): String{
        return fakeDataRepository.fetchDataFromDatabase()
    }

}