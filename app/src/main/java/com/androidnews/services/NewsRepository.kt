package com.androidnews.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.Config.pageSize
import com.androidnews.data.ArticleList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject
constructor(private val newsService: NewsService) {

    fun getArticleList(query: String, page : Int): LiveData<Result<ArticleList>> {
        val data = MutableLiveData<Result<ArticleList>>()
        val pageSize = pageSize
        newsService.getArticleList(query, page = page, pageSize = pageSize).enqueue(object : Callback<ArticleListResponse> {

            override fun onResponse(call: Call<ArticleListResponse>, response: Response<ArticleListResponse>) {
                data.value = Result(data = response.body().toArticleList(page, pageSize))
            }

            override fun onFailure(call: Call<ArticleListResponse>, t: Throwable) {
                data.value = Result(error = t)
            }
        })

        return data
    }
}
