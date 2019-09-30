package com.androidnews.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

/**
 * util class to help read data in assets folder
 */
object Assets {

    @Throws(IOException::class)
    fun readStream(ctx: Context, path: String): InputStream {
        return ctx.resources.assets.open(path)
    }

    @Throws(IOException::class)
    fun readString(ctx: Context, path: String): String {
        return readStream(ctx, path).use {
            String(it.readBytes())
        }
    }

}

