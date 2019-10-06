package com.androidnews.repository.db

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.AndroidTestHelper
import com.androidnews.di.AppModule
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ArticleDaoTest : AndroidTest() {
    @Test
    fun testDao() {
        val query = "test"
        val dao = AppModule().provideAppDatabase(ctx).articleDao()

        dao.deleteAll()

        Assert.assertEquals(0, dao.getAll().size)
        dao.putAll(
            AndroidTestHelper.createArticle(query, "asdsasdad").apply {
                publishedAt = Date()
            },

            AndroidTestHelper.createArticle(query, "dsfdasdsf").apply {
                publishedAt = Date().also {
                    it.time -= 1000 * 60 * 60 * 24
                }
            },

            AndroidTestHelper.createArticle(query, "adzxczxc").apply {
                publishedAt = Date().also {
                    it.time -= 1000 * 60 * 60 * 24 * 2
                }
            },

            AndroidTestHelper.createArticle(query, "retterter").apply {
                publishedAt = Date().also {
                    it.time -= 1000 * 60 * 60 * 24 * 3
                }
            },

            AndroidTestHelper.createArticle(query, "vbvcbcvb").apply {
                publishedAt = Date().also {
                    it.time -= 1000 * 60 * 60 * 24 * 4
                }
            }


        )

        Assert.assertEquals(5, dao.getAll().size)

        Assert.assertEquals(5, dao.getCount(query, Date().time))


        val page = dao.get(query, Date().time, 2)
        Assert.assertEquals(2, page.size)

        Assert.assertEquals(true, page.first().title?.contains("asdsasdad"))


        Assert.assertEquals("retterter", dao.getById("retterter").id)


    }
}
