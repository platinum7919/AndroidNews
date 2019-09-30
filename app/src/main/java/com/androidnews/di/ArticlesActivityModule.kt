package com.androidnews.di

import com.androidnews.ui.article.ArticlesActivity
import dagger.Module

@Module
abstract class ArticlesActivityModule {
    abstract fun contributeArticlesActivity(): ArticlesActivity
}
