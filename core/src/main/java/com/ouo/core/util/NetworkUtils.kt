package com.ouo.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

/**
 * Created by oxq on 2024/1/11.
 *
 * 检查网络可用
 */

const val TAG = "checkNetworkAvailable"

//需要在 Android 系统版本号大于或等于 23（即 Android 6.0，API Level 23）时才能被调用或使用
//告诉编译器在指定代码段中忽略权限缺失的警告
@SuppressLint("MissingPermission")
fun Context?.checkNetworkAvailable(): Boolean {
    if (this == null) {
        return true
    }
    //检查是否有可用网络
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val network = connectivityManager?.activeNetwork
    return if (network == null) {
        Log.w(TAG, "当前无网络")
        false
    } else {
        //检查当前设备的网络连接类型
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) != false) {
            Log.w(TAG, "正在使用移动网络")
        }
        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) != false) {
            Log.w(TAG, "正在使用WIFI")
        }
        true
    }


}