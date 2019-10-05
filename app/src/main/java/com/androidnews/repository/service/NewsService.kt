package com.androidnews.repository.service

import com.androidnews.Config
import com.androidnews.data.ServerError
import com.androidnews.repository.ArticleListResponse
import com.androidnews.utils.Json
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface NewsService {
    companion object {
        val baseUrl = "https://newsapi.org/"
    }

    @GET("v2/everything")
    fun getArticles(
        @Query("q") query: String,
        @Query("from") from: String = getQueryStartDate(),
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = Config.apiKey,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("language") language: String = Config.language
    ): Single<ArticleListResponse>
}

fun getQueryStartDate(): String {
    val lastMonth = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, -30)
    }
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply {
        timeZone = lastMonth.timeZone
    }.format(lastMonth.time)
}


class ServerException(val json: Json, response: Response) : Exception() {
    val code: Int
    val servrError: ServerError?
    val defaultMessage: String?

    init {
        code = response.code()
        servrError = response.body()?.string()?.let { errorText ->
            json.toObject<ServerError>(errorText)
        }
        defaultMessage = "Status code: ${code}"
    }

    override val message: String?
        get() {
            return (servrError?.message ?: defaultMessage)
        }
}

class ServerErrorInterceptor(val json : Json) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val code = response.code()
        if(code in 400..599){
            throw ServerException(json, response)
        }
        return response
    }
}