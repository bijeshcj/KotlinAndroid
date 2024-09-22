package com.bijesh.coroutine.retrofit.apis

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val BASE_URL_LOCAL = "http://10.0.2.2:8080/" // use this for emulator
    private const val BASE_URL_MY_IP_ADDRESS = "http://192.168.0.143:8080/" // use this for real device

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_LOCAL)//BASE_URL_LOCAL
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val localApi: LocalApi by lazy {
        retrofit.create(LocalApi::class.java)
    }
}