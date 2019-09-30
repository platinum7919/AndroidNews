package com.androidnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnews.di.ViewModelSubComponent
import javax.inject.Inject
import javax.inject.Singleton


typealias ViewModelCreator = () -> ViewModel

@Singleton
class AppViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {
    private val creators : Map<Class<*>, ViewModelCreator>

    init {

        creators = mutableMapOf<Class<*>, ViewModelCreator>().also {
            it[ArticleViewModel::class.java] = {
                viewModelSubComponent.articlesViewModel()
            }
        }
        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
    }

    @Suppress("UNCHECKED_CAST")
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
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }
        try {
            return creator.invoke() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}