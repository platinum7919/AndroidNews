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
    fun toArticleList(page: Int, pageSize: Int): ArticleList {
        val totalPage: Int = ((totalResults ?: 0) / pageSize) + (if ((totalResults ?: 0) % pageSize == 0) 1 else 0);
        return ArticleList(pageSize = pageSize, page = page, totalPages = totalPage, articlesList = articlesList);
    }
}

class Result<D>(
    val data: D? = null,
    val error: Throwable? = null
)