package com.androidnews.di

import android.app.Application
import android.content.Context
import com.androidnews.App
import com.androidnews.BuildConfig
import com.androidnews.Config
import com.androidnews.services.NewsRepository
import com.androidnews.services.NewsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule  {

    @Provides
    @Singleton
    fun provideApplication(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideNewsService() : NewsService {
        return Retrofit.Builder()
            .baseUrl(NewsService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }
}