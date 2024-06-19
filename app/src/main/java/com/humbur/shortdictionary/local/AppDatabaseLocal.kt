package com.humbur.shortdictionary.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.humbur.shortdictionary.local.dao.WordsDao
import com.humbur.shortdictionary.model.WordsEntity

@Database(
    entities = [WordsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabaseLocal : RoomDatabase() { // More descriptive name

    abstract fun wordsDao(): WordsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabaseLocal? = null

        fun getDatabase(context: Context): AppDatabaseLocal = // Pass Context as parameter
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, // Use application context for safety
                AppDatabaseLocal::class.java,
                "dictionary"
            ).build()
    }
}