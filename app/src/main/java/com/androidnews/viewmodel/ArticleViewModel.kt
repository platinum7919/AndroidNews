package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnews.data.Article
import com.androidnews.repository.NewsRepository



class ArticleViewModel(app : Application, var newsRepository: NewsRepository) : BaseViewModel(app) {

    val viewingArticle: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }


}

