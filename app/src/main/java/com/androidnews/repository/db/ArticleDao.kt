package com.androidnews.repository.db

import androidx.room.*
import com.androidnews.data.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Query("SELECT * FROM articles WHERE id LIKE :id")
    fun findById(id: String): List<Article>


    @Query("SELECT * FROM articles WHERE published_at < :publishAfter AND query_value = :query ORDER BY published_at DESC LIMIT :pageSize")
    fun query(query : String, publishAfter : Long, pageSize : Int): List<Article>

    @Query("SELECT COUNT(*) FROM articles WHERE published_at < :publishAfter AND query_value = :query")
    fun getQueryCount(query : String, publishAfter : Long): Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(vararg articles: Article)

    @Delete
    fun delete(article: Article)

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun update(vararg articles: Article)

}

