package com.bijesh.coroutine.retrofit.apis

import com.bijesh.coroutine.retrofit.models.LocalUser
import com.bijesh.coroutine.retrofit.models.User
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface LocalApi {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("user")
    fun getLocalUser(): Call<LocalUser>

    @GET("user/allUsers")
    fun getAllUsers(): Call<List<LocalUser>>

    @GET("/downloadPDF")
    fun downloadPDF(): Call<ResponseBody>

    @GET("/downloadPDF")
    @Streaming
    fun downloadPDFRx(): Single<ResponseBody>
}