package com.androidnews.di

import com.androidnews.viewmodel.ArticleViewModel
import dagger.Subcomponent

/**
 * A sub component to create ViewModels. It is called by the
 * [com.androidnews.ui.article.ArticleViewModelFactory]
 */
@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun articlesViewModel(): ArticleViewModel
}
