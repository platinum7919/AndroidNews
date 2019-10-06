package com.androidnews.common

import android.content.Context
import com.androidnews.R
import java.net.SocketException


/**
 * get a more user-friendly message
 */
fun Throwable.getUserMessage(ctx: Context): String {
    val defaultMsg = this.message ?: "Unknown error"
//    if (BuildConfig.DEBUG) {
//        return defaultMsg
//    }

    if (this is SocketException
        || this is java.net.NoRouteToHostException
        || this is java.net.ConnectException
        || this is java.net.UnknownHostException
        || this is java.net.SocketTimeoutException
    ) {
        return ctx.getString(R.string.error_connection_error)
    } else {
        return defaultMsg
    }
}