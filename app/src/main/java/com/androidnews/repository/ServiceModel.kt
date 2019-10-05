package com.androidnews.repository

import com.androidnews.data.Article
import com.androidnews.data.ArticlePage
import com.google.gson.annotations.SerializedName

abstract class Response {
    abstract val status: String?
    val isOk get() = status == "ok"
}

class ArticleListResponse(
    @SerializedName("status") override val status: String?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("articles") val articlesList: List<Article>?
) : Response() {
    fun toArticleList(queryId: String, page: Int, pageSize: Int): ArticlePage {
        return ArticlePage(queryId, pageSize, articlesList ?: mutableListOf(), page, totalResults ?: 0)
    }
}

class Result<D>(
    var dataSource : DataSource,
    var fetching: Boolean = false,
    val data: D? = null,
    val error: Throwable? = null
) {
    val isSuccess: Boolean get() = error == null
    val isFetching: Boolean get() = fetching || (data == null && error == null)
}

enum class DataSource {
    Api, Db
}