package com.androidnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.data.ArticleList
import javax.inject.Inject


class ArticleListViewModel(app: Application) : AndroidViewModel(app) {

    private val article: MutableLiveData<ArticleList> by lazy {
        MutableLiveData<ArticleList>().also {
            loadData()
        }
    }

    fun getArticleList(): LiveData<ArticleList> {
        return article
    }


    fun loadData(){

    }
}

