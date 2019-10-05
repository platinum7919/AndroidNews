package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.androidnews.data.ArticlePage
import com.androidnews.repository.NewsRepository
import com.androidnews.repository.Result
import javax.inject.Singleton

@Singleton
class ArticlePageViewModel constructor(app: Application, var newsRepository: NewsRepository) : BaseViewModel(app) {

    val articlePage: LiveData<Result<ArticlePage>> by lazy {
        newsRepository.articlePage
    }

    private val query: String?
        get() {
            return articlePage.value?.data?.query
        }

    private val pageNum: Int?
        get() {
            return articlePage.value?.data?.pageNum
        }

    private val totalPages: Int?
        get() {
            return articlePage.value?.data?.totalPages
        }

    val hasMoreToLoad: Boolean get() = pageNum == null || (totalPages ?: 0) > (pageNum ?: 0)

    fun loadData() {
        if (hasMoreToLoad) {
            newsRepository.getArticlePage(query = query ?: "TransferWise", page = (pageNum ?: 0) + 1)
        }
    }

}
