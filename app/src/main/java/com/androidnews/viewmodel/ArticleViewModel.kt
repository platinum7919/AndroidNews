package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.App
import com.androidnews.data.Article
import javax.inject.Inject


class ArticleViewModel(app : Application) : BaseViewModel(app) {

    private val article: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }

    fun getArticle(): LiveData<Article> {
        return article
    }

}

