package com.androidnews.repository

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.AndroidTestHelper
import com.androidnews.ExplicitObserver
import com.androidnews.data.ArticlePage
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
class NewsRepositoryTest : AndroidTest() {


    val mockInstance = NewsRepository(AndroidTestHelper.mockNewsService, AppModule().provideAppDatabase(ctx))

    val json = AppModule().provideJson()


    /**
     * Attempt to fetch 3 pages
     *
     * - The test will observe the [LiveData] of [Result] of [ArticlePage]
     * - It will call getArticlePage starting with page 1
     * - It will then observe the page 1 and set the next page to 2
     * - The test will getArticlePage with page 2
     * - It will then observe the page 2 and set the next page to 3
     *
     * - this will continue for page 3
     */
    @Test
    fun testInstanceOnWorkerThread() {
        val query = "TransferWise"
        var page = 1
        var itemCount = 0
        var loadMore = true
        var observer: ExplicitObserver<*>? = null
        try {
            observer = ExplicitObserver<Result<ArticlePage>>(handler = {
                if(!it.isFetching) {
                    Timber.v("Expecting page ${page}, result pageNum = ${it.data?.pageNum}")
                    val fetchedResult = it.data!!
                    Assert.assertEquals("Expecting page ${page}", page, fetchedResult.pageNum)

                    val resultItemCount = it.data?.list?.size ?: 0

                    Assert.assertTrue("Expecting ArticlePage to be merged with existing result", resultItemCount > itemCount)

                    itemCount = resultItemCount

                    Timber.v("Getting ArticlePage from live data. ${it.dataSource} ${json.toJson(fetchedResult)}")
                    page++
                    loadMore = true
                }
            })


            runOnMainThread {
                mockInstance.articlePage.observe(observer, observer)
            }

            Thread.sleep(1000)

            while (page <= 3) {
                if (loadMore) {
                    loadMore = false
                    Timber.v("Getting ArticlePage page = ${page}")
                    mockInstance.getArticlePage(query, page)
                }
                Thread.sleep(1000)
            }


        } catch (e: Throwable) {
            Timber.e(e)
        } finally {
            runOnMainThread {
                observer?.destroy()
            }
        }

    }
}
