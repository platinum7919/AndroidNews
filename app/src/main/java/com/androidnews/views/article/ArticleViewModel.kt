package com.androidnews.views.article

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.data.Article
import com.androidnews.repository.NewsRepository
import com.androidnews.viewmodel.BaseViewModel
import javax.inject.Inject


class ArticleViewModel  @Inject constructor(app: Application, var newsRepository: NewsRepository) : BaseViewModel(app) {

    private val article: MutableLiveData<Article> by lazy {
        newsRepository.article
    }

    fun getArticle(id: String) : LiveData<Article> {
        return newsRepository.getArticle(id)
    }

}

