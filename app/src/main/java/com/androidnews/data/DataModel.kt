package com.androidnews.data

import com.google.gson.annotations.SerializedName


interface PaginatedList<K> {
    val queryId: String
    val pageSize: Int
    var page: Int
    var totalItems: Int
    var list: List<K>

    val totalPages: Int
        get() {
            return (totalItems / pageSize) + (if (totalItems % pageSize == 0) 1 else 0)
        }


    fun isAppendingPossible(paginatedList: PaginatedList<K>, checkPageNum: Boolean = false): Boolean {
        return paginatedList.queryId == queryId && paginatedList.pageSize == pageSize &&
                (!checkPageNum || page + 1 == paginatedList.page)
    }

    fun append(paginatedList: PaginatedList<K>) {
        if (!isAppendingPossible(paginatedList)) {
            throw IllegalArgumentException("Cannot append articleList from different queries")
        }
        if (page + 1 == paginatedList.page) {
            page = paginatedList.page
            totalItems = paginatedList.totalItems

            list = list.toMutableList().apply {
                addAll(paginatedList.list)
            }
        }
    }
}

class ArticleList(
    override val queryId: String,
    override val pageSize: Int,
    override var list: List<Article>,
    override var page: Int = 0,
    override var totalItems: Int = 0
) : PaginatedList<Article>


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