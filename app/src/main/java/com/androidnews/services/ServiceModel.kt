package com.androidnews.services

import com.androidnews.data.Article
import com.androidnews.data.ArticleList
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
    fun toArticleList(queryId: String, page: Int, pageSize: Int) : ArticleList{
        return ArticleList(queryId, pageSize, articlesList ?: mutableListOf(), page, totalResults ?: 0)
    }
}

class Result<D>(
    val data: D? = null,
    val error: Throwable? = null
) {
    val isSuccess: Boolean get() = error == null
}