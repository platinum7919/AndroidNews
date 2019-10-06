package com.androidnews.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androidnews.data.Article
import java.util.*


/**
 * App's database
 */
@Database(entities = [Article::class], version = 1)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}

/**
 * [RoomDatabase] doesn't support [Date] as column type directly.
 *
 * This is a converter to fix that
 */
class DateConverter {
    @TypeConverter
    fun toDate(epochMs: Long?): Date? {
        return epochMs?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}