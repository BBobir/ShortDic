package com.humbur.shortdictionary.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.humbur.shortdictionary.local.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertFavorite(favorite: Favorite)


    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Query("SELECT COUNT(*) FROM favorite WHERE dicId = :id")
    suspend fun getFavoriteById(id: Int): Int

    @Query("DELETE FROM favorite WHERE dicId = :id")
    suspend fun deleteFavorite(id: Int)





}