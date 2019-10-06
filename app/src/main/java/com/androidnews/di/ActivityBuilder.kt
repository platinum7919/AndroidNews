package com.androidnews.di

import com.androidnews.views.article.ArticlePageActivity
import com.androidnews.views.article.ViewArticleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    //@ContributesAndroidInjector(modules = [ArticlesActivityModule::class])
    @ContributesAndroidInjector
    abstract fun buildArticlesActivity(): ArticlePageActivity

    @ContributesAndroidInjector
    abstract fun buildViewArticleActivity(): ViewArticleActivity

}