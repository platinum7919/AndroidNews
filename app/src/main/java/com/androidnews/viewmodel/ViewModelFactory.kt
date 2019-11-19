package com.androidnews.viewmodel

import android.app.Application
import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnews.di.ViewModelSubComponent
import com.androidnews.repository.NewsRepository
import com.androidnews.views.article.ArticleViewModel
import com.androidnews.views.customviews.ArticlePageViewModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {
    private val creators: ArrayMap<Class<*>, () -> ViewModel>

    init {
        creators = ArrayMap()
        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
        creators[ArticlePageViewModel::class.java] = { viewModelSubComponent.articlePageViewModel() }
        creators[ArticleViewModel::class.java] = { viewModelSubComponent.articleViewModel() }

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        requireNotNull(creator) { "Unknown model class $modelClass" }
        try {
            return creator() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}