package com.gmail.segenpro.simpleweather.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun getFullUrlFromProtocolRelative(protocolRelativeUrl: String): String {
    val iconUri = Uri.parse(protocolRelativeUrl)
    return Uri.Builder()
            .scheme("https")
            .authority(iconUri.authority)
            .path(iconUri.path)
            .build()
            .toString()
}
