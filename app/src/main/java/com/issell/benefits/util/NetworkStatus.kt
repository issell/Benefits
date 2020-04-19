package com.issell.benefits.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkStatus : ConnectivityManager.NetworkCallback(){
    companion object {
        fun isNetworkConnected(context: Context):Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if(cm.isDefaultNetworkActive){
                return true
            }
            return false
        }
    }
}