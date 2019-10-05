package com.androidnews.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.androidnews.repository.db.AppDatabase
import com.androidnews.repository.service.NewsService
import com.androidnews.repository.service.ServerErrorInterceptor
import com.androidnews.utils.Json
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(application: Application): Context {
        return application
    }


    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json()
    }


    @Inject
    @Provides
    @Singleton
    fun provideServerErrorInterceptor(json: Json): ServerErrorInterceptor {
        return ServerErrorInterceptor(json)
    }


    @Inject
    @Provides
    @Singleton
    fun provideOkHttpClient(serverErrorInterceptor: ServerErrorInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(serverErrorInterceptor)
            .build()
    }

    @Inject
    @Provides
    @Singleton
    fun provideNewsService(okHttpClient: OkHttpClient): NewsService {
        return Retrofit.Builder()
            .baseUrl(NewsService.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }


    @Inject
    @Provides
    @Singleton
    fun provideAppDatabase(ctx: Context): AppDatabase {
        return Room.databaseBuilder(
            ctx.applicationContext,
            AppDatabase::class.java, "android_news_schema7.Db"
        ).build()
    }


}