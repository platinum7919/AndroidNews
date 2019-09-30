package com.androidnews.ui.article

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnews.data.Articles
import com.androidnews.utils.AppPrefs
import com.androidnews.views.*
import com.pixplicity.easyprefs.library.Prefs
import java.lang.Exception


class ArticlesViewModel(val ctx: Context) : ViewModel() {


    private val asyncState: MutableLiveData<AsyncState> by lazy {
        MutableLiveData<AsyncState>()
    }


    private val articles: MutableLiveData<Articles> by lazy {
        MutableLiveData<Articles>().also {
            loadArticles();
        }
    }

    fun getArticles(): LiveData<Articles> {
        return articles
    }



    private fun loadArticles() {
        asyncState.postValue(LoadingState())

        var result = Articles("a")
        result = articles.value?.append(result) ?: result

        // merge with articles
        asyncState.postValue(SuccessState())
        articles.postValue(result)

        asyncState.postValue(ErrorState(Exception("some error")))

    }


}

class ArticleViewModelFactory(val ctx: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticlesViewModel(ctx) as T
    }
}