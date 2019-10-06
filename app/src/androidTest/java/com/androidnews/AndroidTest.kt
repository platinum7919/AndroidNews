package com.androidnews

import android.os.Handler
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class AndroidTest {
    open val ctx = InstrumentationRegistry.getTargetContext()

    open val app = ctx.applicationContext as App

    fun runOnMainThread(runnable: () -> Unit) {
        Handler(ctx.mainLooper).post(runnable);
    }

    fun runOnMainThread(runnable: Runnable) {
        Handler(ctx.mainLooper).post(runnable);
    }

}


