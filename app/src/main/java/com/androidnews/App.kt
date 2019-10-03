package com.androidnews

import android.app.Activity
import android.app.Application
import android.content.Context
import com.androidnews.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

lateinit var appCtx: Context

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        appCtx = this.applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent
            .builder()
            .application(this).build()
            .inject(this)
    }
}

object Config {
    val apiKey: String = "659153112f23422db12fc3b0a43c61a5"
    val pageSize: Int = 20
    var language: String = "en"
}