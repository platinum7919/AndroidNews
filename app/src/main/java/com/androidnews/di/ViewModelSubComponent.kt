package com.androidnews.di

import com.androidnews.views.article.ArticleViewModel
import com.androidnews.views.customviews.ArticlePageViewModel
import dagger.Subcomponent


/**
 */
@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun articleViewModel(): ArticleViewModel

    fun articlePageViewModel(): ArticlePageViewModel
}
