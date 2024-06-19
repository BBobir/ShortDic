package com.humbur.shortdictionary.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.humbur.shortdictionary.model.WordsEntity

@Dao
interface WordsDao {

    @Insert
    suspend fun insert(word: WordsEntity): Long

    @Query("DELETE FROM WordsEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM WordsEntity")
    suspend fun getAll(): List<WordsEntity>


}