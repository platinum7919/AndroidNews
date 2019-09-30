package com.androidnews.data

import com.google.gson.annotations.SerializedName


class ArticleList(
    val pageSize: Int,
    var articlesList: List<Article>? = null,
    var page: Int = 0,
    var totalPages: Int = 0
) {
    fun append(articleList: ArticleList): ArticleList {
        if (articleList.pageSize != pageSize) {
            throw IllegalArgumentException("Cannot append articleList from different queries")
        }
        if (page + 1 == articleList.page) {
            page = articleList.page
            totalPages = articleList.totalPages
            articlesList = mutableListOf<Article>().also {
                it.addAll(articlesList ?: listOf())
            }
        }
        return this
    }
}



class Source(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
)

class Article(
    @SerializedName("source") val source: Source?,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
)