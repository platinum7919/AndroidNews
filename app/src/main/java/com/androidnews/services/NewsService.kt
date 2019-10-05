package com.androidnews.services

import com.androidnews.Config
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface NewsService {
    companion object {
        val baseUrl = "https://newsapi.org/"
    }

    @GET("v2/everything")
    abstract fun getArticleList(
        @Query("q") query: String,
        @Query("from") from: String = _getQueryStartDate(),
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = Config.apiKey,
        @Query("pageSize") pageSize: Int = Config.pageSize,
        @Query("page") page: Int,
        @Query("language") language: String = Config.language
    ): Call<ArticleListResponse>
}

fun _getQueryStartDate(): String {
    val lastMonth = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, -30)
    }
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply {
        timeZone = lastMonth.timeZone
    }.format(lastMonth.time)
}