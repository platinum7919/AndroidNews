package com.androidnews.di

import androidx.lifecycle.ViewModelProvider
import com.androidnews.services.NewsService
import com.androidnews.viewmodel.AppViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module(subcomponents = [ViewModelSubComponent::class])
internal class AppModule {
    @Singleton
    @Provides
    fun provideNewsApiService(): NewsService {
        return Retrofit.Builder()
            .baseUrl(NewsService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(
        viewModelSubComponent: ViewModelSubComponent.Builder
    ): ViewModelProvider.Factory {
        return AppViewModelFactory(viewModelSubComponent.build())
    }
}
