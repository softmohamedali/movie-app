package com.example.pophop.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetWorkListener:ConnectivityManager.NetworkCallback() {

    val networkIsAvilibale= MutableStateFlow(true)

    fun cheakIsConnect(context: Context):MutableStateFlow<Boolean>
    {
        var isConnect=false
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        connectivityManager.allNetworks.forEach {
            var networkCbability=connectivityManager.getNetworkCapabilities(it)
            networkCbability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                {
                    isConnect=true
                    return@forEach
                }
            }
        }
        networkIsAvilibale.value=isConnect
        return networkIsAvilibale
    }

    override fun onAvailable(network: Network) {
        networkIsAvilibale.value=true
    }

    override fun onLost(network: Network) {
        networkIsAvilibale.value=false
    }
}