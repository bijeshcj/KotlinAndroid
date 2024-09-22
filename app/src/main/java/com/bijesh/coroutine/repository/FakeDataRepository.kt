package com.bijesh.coroutine.repository

import kotlinx.coroutines.delay

class FakeDataRepository {

    suspend fun fetchDataFromNetwork(): String{
        delay(2000L)
        return "Network Data"
    }

    suspend fun fetchDataFromDatabase(): String{
        delay(1000L)
        return "Database data"
    }

}