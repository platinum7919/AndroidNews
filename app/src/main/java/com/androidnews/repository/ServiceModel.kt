package com.androidnews.repository

import com.androidnews.data.Article
import com.androidnews.data.ArticlePage
import com.androidnews.utils.generateKey
import com.androidnews.utils.toEpochDateString
import com.google.gson.annotations.SerializedName

abstract class Response {
    abstract val status: String?
    val isOk get() = status == "ok"
}

class ArticleListResponse(
    @SerializedName("status") override val status: String?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("articles") val articles: List<Article>?
) : Response() {


    /**
     * Also assign [Article.id] an unique keys so that we can identify each [Article] uniquely
     *
     * (fyi, there is no uniqie field per article returned by the API), but we will need one if
     *
     * we are to insert these object in db.
     */
    fun toArticlePage(query: String, page: Int, pageSize: Int): ArticlePage {
        articles?.forEach {
            it.query = query
            // generate a unique key for this article object
            it.id = generateKey(
                it.title,
                it.source?.id,
                it.source?.name,
                it.publishedAt?.toEpochDateString()
            )
        }

        return ArticlePage(query, pageSize, articles ?: mutableListOf(), page, totalResults ?: 0)
    }
}

class Result<D>(
    var dataSource: DataSource,
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