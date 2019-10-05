package com.androidnews.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.androidnews.utils.Json
import com.google.gson.annotations.SerializedName
import java.util.*


interface PaginatedList<K> {
    val pageSize: Int
    var pageNum: Int
    var totalItems: Int
    var list: List<K>

    val totalPages: Int
        get() {
            return (totalItems / pageSize) + (if (totalItems % pageSize == 0) 0 else 1)
        }


    fun isAppendingPossible(paginatedList: PaginatedList<K>, checkPageNum: Boolean = false): Boolean {
        return paginatedList.pageSize == pageSize &&
                (!checkPageNum || pageNum + 1 == paginatedList.pageNum)
    }

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

class ArticlePage(
    val query: String,
    override val pageSize: Int,
    override var list: List<Article>,
    override var pageNum: Int = 0,
    override var totalItems: Int = 0
) : PaginatedList<Article> {

    override fun isAppendingPossible(paginatedList: PaginatedList<Article>, checkPageNum: Boolean): Boolean {
        val paginatedListQuery = if (paginatedList is ArticlePage) {
            paginatedList.query
        } else null
        return paginatedListQuery == query && super.isAppendingPossible(paginatedList, checkPageNum)
    }
}


data class Source(
    @ColumnInfo(name = "source_id") @SerializedName("id") val id: String?,
    @ColumnInfo(name = "source_name") @SerializedName("name") val name: String?
)


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
) : JsonObject


//@Entity(tableName = "accounts", primaryKeys = ["id"])
//class Account(
//    @Embedded var company: Company? = null,
//    var name: String? = null,
//    var age: Int? = null,
//    var email: String,
//    var domain: String,
//    var date: Date? = null,
//    var id: String = "$email/$domain"
//) : JsonObject {
//}
//
//class Company(
//    var companyName: String?,
//    var companyUrl: String?,
//    var companyId: String = "$companyName/$companyUrl"
//) : JsonObject {
//}


interface JsonObject {
    fun toJson(json: Json): String? {
        return json.toJson(this)
    }
}


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
