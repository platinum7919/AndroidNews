package com.androidnews.di

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.repository.db.AppDatabase
import com.androidnews.repository.service.NewsService
import com.androidnews.repository.service.ServerErrorInterceptor
import com.androidnews.utils.Json
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppModuleTest : AndroidTest() {
    @Test
    fun testAppModule() {
        val instance = AppModule()


        lateinit var json: Json
        lateinit var appDb: AppDatabase
        Assert.assertNotNull("Not null AppDatabase", instance.provideAppDatabase(ctx).apply {
            appDb = this
        })
        Assert.assertNotNull("Not null Application ", instance.provideApplication(app))
        Assert.assertNotNull("Not null Json", instance.provideJson().apply {
            json = this
        })

        lateinit var sei: ServerErrorInterceptor
        Assert.assertNotNull("Not null ServerErrorInterceptor", instance.provideServerErrorInterceptor(json).apply {
            sei = this
        })

        lateinit var okhttpClient: OkHttpClient
        Assert.assertNotNull("Not null OkHttpClient", instance.provideOkHttpClient(sei).apply {
            okhttpClient = this
        })


        lateinit var newservice: NewsService
        Assert.assertNotNull("Not null NewsService", instance.provideNewsService(okhttpClient).apply {
            newservice = this
        })
    }
}
