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

    val articleList: MutableLiveData<Result<ArticleList>> by lazy {
        MutableLiveData<Result<ArticleList>>()
    }


    init {

    }

    fun getArticleList(query: String, page: Int): LiveData<Result<ArticleList>>{
        fetchArticleList(query, page)
        return articleList;
    }



    private fun fetchArticleList(query: String, page: Int) {
        val pageSize = pageSize

        articleList.postFetching(true)
        newsService.getArticleList(query, page = page, pageSize = pageSize)
            .enqueue(object : Callback<ArticleListResponse> {
                override fun onResponse(call: Call<ArticleListResponse>, response: Response<ArticleListResponse>) {

                    val fetchedData = response.body().toArticleList(query, page, pageSize)

                    val mergedData = articleList.value?.data?.let {
                        existingData ->
                        if(existingData.isAppendingPossible(fetchedData)){
                            existingData.apply {
                                append(fetchedData)
                            }
                        }else{
                            existingData
                        }
                    } ?: run {
                        fetchedData
                    }

                    articleList.postValue(Result(data = mergedData))
                }

                override fun onFailure(call: Call<ArticleListResponse>, t: Throwable) {
                    articleList.postValue(Result(error = t))
                }
            })
    }

}


fun <K> MutableLiveData<Result<K>>.postFetching(fetching : Boolean){
    this.value?.let {
            existing ->
        this.postValue(existing.also {
            it.fetching = fetching
        })
    }
}