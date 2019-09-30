package com.androidnews.data

import com.google.gson.annotations.SerializedName


class Articles(
    var articles : List<Article>? = null,
    var page : Int = 0
){

}

class Source(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?)

class Article(
    @SerializedName("source") val source: Source?,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
) {
    
}