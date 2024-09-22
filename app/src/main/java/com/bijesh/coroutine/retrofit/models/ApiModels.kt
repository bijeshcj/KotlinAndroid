package com.bijesh.coroutine.retrofit.models

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)


data class LocalUser(
    val id:Int,
    val name:String,
    val email:String
)