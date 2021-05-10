package com.example.pophop.utils

sealed class NetworkResult<T> (
        val data:T?=null,
        val massage:String?=null
        ){

    class OnSuccsess<T>(data: T): NetworkResult<T>(data)
    class Error<T>(massage: String?,data: T?=null): NetworkResult<T>(data,massage)
    class Loading<T>(): NetworkResult<T>()
}