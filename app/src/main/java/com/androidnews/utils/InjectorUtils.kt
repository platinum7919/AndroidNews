package com.androidnews.utils

import android.content.Context
import com.androidnews.ui.article.ArticleViewModelFactory

/**
 * Might later want to use a [Repository] class to handle where the data is coming from
 *
 * inject dao object into [Repository] also
 */
object InjectorUtils {

    fun provideArticleViewModelFactory(ctx: Context): ArticleViewModelFactory {
        return ArticleViewModelFactory(ctx)
    }

}

