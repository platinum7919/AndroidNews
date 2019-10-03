package com.androidnews.di

import com.androidnews.views.article.ArticlesActivity
import com.androidnews.views.article.ArticlesActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ArticlesActivityModule::class])
    abstract fun buildArticlesActivity(): ArticlesActivity
}