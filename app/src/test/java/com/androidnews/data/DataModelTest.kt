package com.androidnews.data

import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataModelTest {


    @Test
    fun test_PaginatedListCheckAppend() {

        val firstPage = MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = MockPaginatedList(5, 2, 12, listOf(6, 7, 8, 9, 10))
        val thirdPage = MockPaginatedList(5, 3, 12, listOf(11, 12))


        Assert.assertTrue(firstPage.isAppendingPossible(secondPage))

        Assert.assertTrue(firstPage.isAppendingPossible(thirdPage))

        Assert.assertFalse(firstPage.isAppendingPossible(thirdPage, checkPageNum = true))

    }

    /**
     * Should not append if pagesize changed
     */
    @Test(expected = IllegalArgumentException::class)
    fun test_PaginatedListAppendError() {

        val firstPage = MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = MockPaginatedList(6, 2, 12, listOf(6, 7, 8, 9, 10))
        firstPage.append(secondPage)

    }

    /**
     * Should not append if pagesize changed
     */
    @Test
    fun test_PaginatedListAppend() {

        val firstPage = MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = MockPaginatedList(5, 2, 12, listOf(6, 7, 8, 9, 10))
        firstPage.append(secondPage)

        val fetchedPage = firstPage;
        Assert.assertEquals(5, fetchedPage.pageSize)
        Assert.assertEquals(2, fetchedPage.pageNum)
        Assert.assertEquals(12, fetchedPage.totalItems)
        Assert.assertEquals(10, fetchedPage.list.size)
        Assert.assertEquals(10, fetchedPage.list.last())




    }

    @Test
    fun test_ArticlePage() {
        val query = "test"
        val firstPage = ArticlePage(query, pageSize = 3, pageNum = 1, totalItems = 7, list = listOf(
            _createArticle(query, "1234"),
            _createArticle(query, "234234"),
            _createArticle(query, "sdasd")
        ))

        val secondPage = ArticlePage(query, pageSize = 3, pageNum = 2, totalItems = 7, list = listOf(
            _createArticle(query, "zxzxxzc"),
            _createArticle(query, "asdsdasdasd"),
            _createArticle(query, "wqwqeqwe")
        ))

        firstPage.append(secondPage)

        val fetchedPage = firstPage;
        Assert.assertEquals(3, fetchedPage.pageSize)
        Assert.assertEquals(2, fetchedPage.pageNum)
        Assert.assertEquals(7, fetchedPage.totalItems)
        Assert.assertEquals(6, fetchedPage.list.size)
        Assert.assertEquals("wqwqeqwe", fetchedPage.list.last().id)
    }


    /**
     * Should not append if pagesize changed
     */
    @Test(expected = IllegalArgumentException::class)
    fun test_ArticlePageAppendError() {
        val query = "test"
        val firstPage = ArticlePage(query, pageSize = 3, pageNum = 1, totalItems = 7, list = listOf(
            _createArticle(query, "1234"),
            _createArticle(query, "234234"),
            _createArticle(query, "sdasd")
        ))

        val secondPage = ArticlePage("another query", pageSize = 3, pageNum = 2, totalItems = 7, list = listOf(
            _createArticle(query, "dsfds"),
            _createArticle(query, "123123"),
            _createArticle(query, "zxczczx")
        ))
        firstPage.append(secondPage)

    }


}

fun _createArticle(query: String, r: String): Article {
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



