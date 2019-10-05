package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.androidnews.data.ArticleList
import com.androidnews.services.NewsRepository
import com.androidnews.services.Result
import javax.inject.Inject


class ArticleListViewModel constructor(app: Application, var newsRepository: NewsRepository) : BaseViewModel(app) {


    val articleList: LiveData<Result<ArticleList>> by lazy {
        newsRepository.articleList
    }

    var currentQuery = ArticleQuery(query = "Pokemon", page = 1)

    init {
        articleList.observe(this, Observer {

        })
    }

    fun onCreate(){
        newsRepository.getArticleList(currentQuery.query, currentQuery.page)
    }

    fun loadMore(){
        newsRepository.getArticleList(currentQuery.query, currentQuery.page + 1)
    }

}


class ArticleQuery(var query: String, var page: Int)
