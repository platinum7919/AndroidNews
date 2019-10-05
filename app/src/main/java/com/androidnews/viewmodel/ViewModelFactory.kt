package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnews.services.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject
constructor(val app: Application) : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var newsRepository: NewsRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) run {
            return ArticleViewModel(app, newsRepository) as T

        }else if (modelClass.isAssignableFrom(ArticleListViewModel::class.java)) run {
            return ArticleListViewModel(app, newsRepository) as T

        }


        throw IllegalArgumentException("Unsupported class ${modelClass.canonicalName}");
    }

}