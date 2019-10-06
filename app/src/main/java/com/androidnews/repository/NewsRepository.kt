package com.androidnews.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnews.Config.pageSize
import com.androidnews.data.Article
import com.androidnews.data.ArticlePage
import com.androidnews.repository.db.AppDatabase
import com.androidnews.repository.db.ArticleDao
import com.androidnews.repository.service.NewsService
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject
constructor(private val newsService: NewsService, private val appDatabase: AppDatabase) {

    val articlePage: MutableLiveData<Result<ArticlePage>> by lazy {
        MutableLiveData<Result<ArticlePage>>()
    }


    val article: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }


    val articleDao: ArticleDao
        get() {
            return appDatabase.articleDao()
        }

    val disposable = CompositeDisposable()

    init {

    }

    fun getArticle(id: String): LiveData<Article> {
        disposable.add(Single.fromCallable { appDatabase.articleDao().getById(id) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                article.postValue(it)
            }, {}))
        return article;
    }


    /**
     * Get a [LiveData] of [Result] of [ArticlePage]
     *
     * - [ArticlePage] can be converted from [ArticleListResponse] from [NewsService]
     * - [ArticlePage] can also be created from [List] of [Article] fetched from [ArticleDao]
     *
     */
    fun getArticlePage(query: String, page: Int): LiveData<Result<ArticlePage>> {
        fetchArticlePage(query, page)
        return articlePage;
    }


    /**
     * Use [NewsService] to fetch [ArticleListResponse]
     *
     * On success:
     * - [ArticleListResponse] is then converted to [ArticlePage]
     * - [ArticlePage] will be inserted to db via [ArticleDao]
     * - [ArticlePage] will be appended to existing value in [articlePage]
     *
     * On error:
     * - [ArticlePage] will be read from db via [ArticleDao]
     * - (in [getFromDb]) If there is data we will also appended to existing value in [articlePage] if existing [Result.dataSource] is [DataSource.Db]
     * - Will return a generated [ArticlePage]
     * - If there is no data in db we will return the [Result] object with the [Throwable] as error
     *
     */
    private fun fetchArticlePage(query: String, page: Int) {
        Timber.v("fetchArticlePage: get:$query, page:$page")
        val pageSize = pageSize

        articlePage.postFetching(true)
        disposable.add(
            newsService.getArticles(query, page = page, pageSize = pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                    { response ->

                        // this is in background thread
                        val fetchedData = response.toArticlePage(query, page, pageSize)
                        Timber.v("fetchArticlePage:(success response) get:$query, page:${fetchedData.pageNum} totalPage:${fetchedData.totalPages} totalItems=${fetchedData.totalItems}")
                        // put fetched data inside db
                        putInDb(fetchedData)
                        val mergedData = articlePage.value?.data?.let { existingData ->
                            if (existingData.isAppendingPossible(fetchedData)) {
                                existingData.apply {
                                    append(fetchedData)
                                }
                            } else {
                                existingData
                            }
                        } ?: fetchedData

                        articlePage.postValue(Result(dataSource = DataSource.Api, data = mergedData))
                    },
                    { e ->
                        // this is in background thread
                        // try to read from db
                        val result = getFromDb(query, pageSize)
                        // var result = Pair(listOf<Article>(), 0)
                        Timber.v("fetchArticlePage: Error , e:${e}")
                        Timber.v("fetchArticlePage:(getFromDb) result size :${result.first.size}, count:${result.second}")
                        if (result.first.isNotEmpty()) {

                            // there is result
                            val cachedPage = ArticlePage(
                                query = query,
                                pageSize = pageSize,
                                list = result.first,
                                pageNum = 0,
                                totalItems = result.second
                            )
                            articlePage.postValue(Result(dataSource = DataSource.Db, data = cachedPage))
                        } else {

                            // no result
                            articlePage.postValue(Result(dataSource = DataSource.Api, error = e))
                        }
                    })
        )
    }


    /**
     * insert [Article] objects to db.
     */
    @WorkerThread
    private fun putInDb(articlePage: ArticlePage) {
        articleDao.putAll(*articlePage.list.toTypedArray())
    }


    /**
     * Return a [List] of [Article] (size [pageSize]) after the last item of the existing list
     *
     * Or just a [List] from current time (size [pageSize])
     *
     * The total count will also be returned
     *
     * Sort by [Article.publishedAt]
     */
    @WorkerThread
    private fun getFromDb(query: String, pageSize: Int): Pair<List<Article>, Int> {
        val existingList = articlePage.value?.data?.list
        val query = { publishedAfter: Date ->
            val list = (existingList ?: listOf()).toMutableList().apply {
                addAll(articleDao.get(query, publishedAfter.time, pageSize))
            }

            val count = articleDao.getCount(query, publishedAfter.time)
            Pair(list, count)
        }

        val lastArticleDate = existingList?.last()?.publishedAt ?: Date()
        return when (articlePage.value?.dataSource) {
            DataSource.Api -> {
                query(Date())
            }

            DataSource.Db -> {
                query(lastArticleDate)
            }
            else -> {
                query(lastArticleDate)
            }
        }
    }

}


fun <K> MutableLiveData<Result<K>>.postFetching(fetching: Boolean) {
    this.value?.let { existing ->
        this.postValue(existing.also {
            it.fetching = fetching
        })
    }
}


