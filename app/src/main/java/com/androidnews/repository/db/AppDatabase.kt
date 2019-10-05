package com.androidnews.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androidnews.data.Article
import java.util.*


@Database(entities = [Article::class], version = 1)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}

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