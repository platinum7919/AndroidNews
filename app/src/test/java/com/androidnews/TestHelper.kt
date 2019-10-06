package com.androidnews

import com.androidnews.data.Article
import com.androidnews.data.PaginatedList
import com.androidnews.data.Source
import java.util.*

object TestHelper {
    fun createArticle(query: String, r: String): Article {
        return Article(
            query = query,
            source = Source("srcid", "src_name"),
            author = "Mr_$r",
            title = "title_$r",
            description = "des_$r",
            url = "https://www.google.com",
            urlToImage = "https://www.google.com",
            publishedAt = Date(),
            content = "content_$r",
            id = "$r"
        )
    }


    class MockPaginatedList(
        override val pageSize: Int,
        override var pageNum: Int,
        override var totalItems: Int,
        override var list: List<Int>
    ) : PaginatedList<Int>

}

