package com.androidnews

import android.app.Application
import android.content.Context
import timber.log.Timber

lateinit var appCtx: Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appCtx = this.applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

object Config {
    val apiKey: String = "659153112f23422db12fc3b0a43c61a5"
    val pageSize: Int = 20
    var language: String = "en"
}