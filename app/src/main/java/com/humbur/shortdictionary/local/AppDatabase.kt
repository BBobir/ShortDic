package com.humbur.shortdictionary.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.humbur.shortdictionary.App
import com.humbur.shortdictionary.local.dao.FavoriteDao
import com.humbur.shortdictionary.local.entity.Favorite


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
   abstract fun favoiteDao(): FavoriteDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.application,
                    AppDatabase::class.java,
                    "DB_NAME"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}