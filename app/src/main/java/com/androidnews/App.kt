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


lateinit var app: App
val appCtx: Context get() = app.applicationContext

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        app = this
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


//    private fun testDb() {
//        TestTask().execute()
//    }



//
//    class TestTask @Inject constructor() : AsyncTask<Unit, Unit, Unit>() {
//        @Inject
//        lateinit var appDatabase: AppDatabase
//
//        @Inject
//        lateinit var json: Json
//
//        init {
//            DaggerAppComponent.builder().application(app).build().inject(this)
//        }
//
//        override fun doInBackground(vararg params: Unit?) {
//            appDatabase.accountDao().insertAll(
//                Account(Company("popsical","popsical.com"),"pa2n", 37, "ng.yat.pan@gmail.com", "gmail.com", Date()),
//                Account(Company("popsical","popsical.com"),"pa3n", 37, "ng.yat.pan@gmail.com", "google.com"),
//                Account(Company("google","google.com"),"li2n", 38, "linzhao7@gmail.com", "gmail.com")
//            )
//            appDatabase.accountDao().getAll().forEach {
//                Timber.v(it.toJson(json))
//            }
//
//        }
//    }