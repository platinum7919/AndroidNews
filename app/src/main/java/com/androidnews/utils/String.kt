package com.androidnews.utils

import java.security.MessageDigest

fun String.nullIfEmpty(): String? {
    return if (isEmpty()) null else this
}

fun String.nullIfEmptyOrBlank(): String? {
    return if (isEmpty() || isBlank()) null else this
}

fun String.toMD5Array(): ByteArray {
    val md = MessageDigest.getInstance("MD5")
    return md.digest(toByteArray()) ?: ByteArray(0)
}

fun String.toMD5String(): String {
    return toMD5Array().toHexString()
}

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }