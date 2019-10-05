package com.androidnews.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toEpochDateString(): String {
    return SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH).format(this)
}