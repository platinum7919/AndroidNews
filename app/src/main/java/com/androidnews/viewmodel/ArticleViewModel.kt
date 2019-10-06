package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.data.Article
import com.androidnews.repository.NewsRepository


class ArticleViewModel(app: Application, var newsRepository: NewsRepository) : BaseViewModel(app) {

    private val article: MutableLiveData<Article> by lazy {
        newsRepository.article
    }

    fun getArticle(id: String) : LiveData<Article> {
        return newsRepository.getArticle(id)
    }

}

