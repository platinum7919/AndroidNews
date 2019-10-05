package com.androidnews.common

import android.content.Intent
import com.androidnews.utils.Json
import timber.log.Timber


inline fun <reified T> Intent.getJsonObject(
    json: Json,
    name: String,
    onParsingError: (Throwable) -> Unit = { /*use an crash tool here*/ }
): T? {
    return getStringExtra(name)?.let {
        return try {
            json.toObject<T>(it)
        } catch (t: Throwable) {
            Timber.w(t)
            onParsingError(t)
            null
        }
    }
}

fun Intent.setJsonObject(json: Json, name: String, obj: Any) {
    putExtra(name, json.toJson(obj))
}
