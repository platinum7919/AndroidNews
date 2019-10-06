package com.androidnews.data

import com.androidnews.TestHelper
import com.androidnews.TestHelper.createArticle
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataModelTest {


    @Test
    fun testPaginatedListCheckAppend() {

        val firstPage = TestHelper.MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = TestHelper.MockPaginatedList(5, 2, 12, listOf(6, 7, 8, 9, 10))
        val thirdPage = TestHelper.MockPaginatedList(5, 3, 12, listOf(11, 12))


        Assert.assertTrue(firstPage.isAppendingPossible(secondPage))

        Assert.assertTrue(firstPage.isAppendingPossible(thirdPage))

        Assert.assertFalse(firstPage.isAppendingPossible(thirdPage, checkPageNum = true))

    }

    /**
     * Should not append if pagesize changed
     */
    @Test(expected = IllegalArgumentException::class)
    fun testPaginatedListAppendError() {

        val firstPage = TestHelper.MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = TestHelper.MockPaginatedList(6, 2, 12, listOf(6, 7, 8, 9, 10))
        firstPage.append(secondPage)

    }

    /**
     * Should not append if pagesize changed
     */
    @Test
    fun testPaginatedListAppend() {

        val firstPage = TestHelper.MockPaginatedList(5, 1, 12, listOf(1, 2, 3, 4, 5))
        val secondPage = TestHelper.MockPaginatedList(5, 2, 12, listOf(6, 7, 8, 9, 10))
        firstPage.append(secondPage)

        val fetchedPage = firstPage;
        Assert.assertEquals(5, fetchedPage.pageSize)
        Assert.assertEquals(2, fetchedPage.pageNum)
        Assert.assertEquals(12, fetchedPage.totalItems)
        Assert.assertEquals(10, fetchedPage.list.size)
        Assert.assertEquals(10, fetchedPage.list.last())




    }

    @Test
    fun testArticlePage() {
        val query = "test"
        val firstPage = ArticlePage(query, pageSize = 3, pageNum = 1, totalItems = 7, list = listOf(
            createArticle(query, "1234"),
            createArticle(query, "234234"),
            createArticle(query, "sdasd")
        ))

        val secondPage = ArticlePage(query, pageSize = 3, pageNum = 2, totalItems = 7, list = listOf(
            createArticle(query, "zxzxxzc"),
            createArticle(query, "asdsdasdasd"),
            createArticle(query, "wqwqeqwe")
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
    fun testArticlePageAppendError() {
        val query = "test"
        val firstPage = ArticlePage(query, pageSize = 3, pageNum = 1, totalItems = 7, list = listOf(
            createArticle(query, "1234"),
            createArticle(query, "234234"),
            createArticle(query, "sdasd")
        ))

        val secondPage = ArticlePage("another query", pageSize = 3, pageNum = 2, totalItems = 7, list = listOf(
            createArticle(query, "dsfds"),
            createArticle(query, "123123"),
            createArticle(query, "zxczczx")
        ))
        firstPage.append(secondPage)

    }


}
