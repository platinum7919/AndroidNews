package com.androidnews.repository

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.AndroidTestHelper
import com.androidnews.di.AppModule
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ServiceModelTest : AndroidTest() {


    val json = AppModule().provideJson()


    /**
     *
     */
    @Test
    fun testArticleUniqueIdGeneration() {
        val articlePage = json.toObject<ArticleListResponse>(AndroidTestHelper.apiEverythingJsonPages[1])?.toArticlePage( "TransferWise",2, 20)
        val uniqueIds = mutableSetOf<String>()
        articlePage?.list?.forEach {
            Timber.v("title=${it.title} id=${it.id}")
            uniqueIds.add(it.id)
        }
        Assert.assertEquals(articlePage?.list?.size, uniqueIds.size)

    }
}
