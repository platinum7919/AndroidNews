package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.androidnews.data.ArticleList
import com.androidnews.services.NewsRepository
import com.androidnews.services.Result


class ArticleListViewModel constructor(app: Application, var newsRepository: NewsRepository) : BaseViewModel(app) {


    val articleList: LiveData<Result<ArticleList>> by lazy {
        newsRepository.articleList
    }

    init {
    }

    private val query: String?
        get() {
            return articleList.value?.data?.queryId
        }

    private val page: Int?
        get() {
            return articleList.value?.data?.page
        }

    private val totalPages: Int?
        get() {
            return articleList.value?.data?.totalPages
        }

    val hasMoreToLoad: Boolean get() = page == null || (totalPages ?: 0) > (page ?: 0)

    fun loadData() {
        if (hasMoreToLoad) {
            newsRepository.getArticleList(query = query ?: "TransferWise", page = (page ?: 0) + 1)
        }
    }

}
