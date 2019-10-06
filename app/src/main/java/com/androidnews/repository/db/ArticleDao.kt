package com.androidnews.repository.db

import androidx.room.*
import com.androidnews.data.Article


/**
 * DAO for [Article]
 */
@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Query("SELECT * FROM articles WHERE id LIKE :id")
    fun getById(id: String): Article


    /**
     * Select [Article] object based on
     * - only select article if [Article.publishedAt] is after [publishAfter]
     * - only select article if [Article.query] matches [query]
     * - limit select to [pageSize] items
     */
    @Query("SELECT * FROM articles WHERE published_at < :publishAfter AND query_value = :query ORDER BY published_at DESC LIMIT :pageSize")
    fun get(query : String, publishAfter : Long, pageSize : Int): List<Article>


    /**
     * Count [Article] object based on
     * - only count article if [Article.publishedAt] is after [publishAfter]
     * - only count article if [Article.query] matches [query]
     */
    @Query("SELECT COUNT(*) FROM articles WHERE published_at < :publishAfter AND query_value = :query")
    fun getCount(query : String, publishAfter : Long): Int

    /**
     * Insert objects into db (replace if [Article.id] already exist)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(vararg articles: Article)

    @Delete
    fun delete(article: Article)


}

