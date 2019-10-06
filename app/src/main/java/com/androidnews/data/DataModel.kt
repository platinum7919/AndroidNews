package com.androidnews.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * A model that wrap a [List] and provides an interface for meta data.
 * - [pageSize] a fix value for the size of EACH page.
 * - [pageNum] a value that represent the number of pages fetched
 * - [totalItems] total number of items if we are to fetch everyone in one go
 * - [list] a list of all the objects fetched from 1..[pageNum]
 *
 * This class will help us with pagination
 */
interface PaginatedList<K> {
    val pageSize: Int
    var pageNum: Int
    var totalItems: Int
    var list: List<K>

    val totalPages: Int
        get() {
            return (totalItems / pageSize) + (if (totalItems % pageSize == 0) 0 else 1)
        }

    /**
     * True if [paginatedList] is has the same [pageSize]
     *
     * if [checkPageNum] is true then we only return true if [paginatedList] is also true
     */
    fun isAppendingPossible(paginatedList: PaginatedList<K>, checkPageNum: Boolean = false): Boolean {
        return paginatedList.pageSize == pageSize &&
                (!checkPageNum || pageNum + 1 == paginatedList.pageNum)
    }


    /**
     * Ignore if [paginatedList] is not the next page to be added
     *
     * If [paginatedList] is the next page then it will update the page number
     * and then also add all [paginatedList].[list] items to [list]
     */
    fun append(paginatedList: PaginatedList<K>) {
        if (!isAppendingPossible(paginatedList)) {
            throw IllegalArgumentException("Cannot append articlePage from different queries")
        }
        if (pageNum + 1 == paginatedList.pageNum) {
            pageNum = paginatedList.pageNum
            totalItems = paginatedList.totalItems

            list = list.toMutableList().apply {
                addAll(paginatedList.list)
            }
        }
    }
}

/**
 * A implementation of [PaginatedList]
 */
class ArticlePage(
    val query: String,
    override val pageSize: Int,
    override var list: List<Article>,
    override var pageNum: Int = 0,
    override var totalItems: Int = 0
) : PaginatedList<Article> {


    /**
     * Override also check the [query], and that we only append if [query] matches [paginatedList].[query]
     */
    override fun isAppendingPossible(paginatedList: PaginatedList<Article>, checkPageNum: Boolean): Boolean {
        val paginatedListQuery = if (paginatedList is ArticlePage) {
            paginatedList.query
        } else null
        return paginatedListQuery == query && super.isAppendingPossible(paginatedList, checkPageNum)
    }
}


/**
 * Source object inside [Article]
 */
data class Source(
    @ColumnInfo(name = "source_id") @SerializedName("id") val id: String?,
    @ColumnInfo(name = "source_name") @SerializedName("name") val name: String?
)

/**
 * Model for article
 */
@Entity(tableName = "articles", primaryKeys = ["id"])
class Article(
    @ColumnInfo(name = "query_value") var query: String?,
    @Embedded @SerializedName("source") var source: Source?,
    @SerializedName("author") var author: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("url") var url: String?,
    @ColumnInfo(name = "url_to_image") @SerializedName("urlToImage") var urlToImage: String?,
    @ColumnInfo(name = "published_at") @SerializedName("publishedAt") var publishedAt: Date?,
    @SerializedName("content") var content: String?,
    @ColumnInfo(name = "id") var id: String
)

/**
 *
 * {
 * "status": "error",
 * "code": "parametersMissing",
 * "message": "Required parameters are missing, the scope of your search is too broad. Please set any of the following required parameters and try again: q, qInTitle, sources, domains."
 * }
 *
 */
class ServerError(
    @SerializedName("status") val status: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?
)
