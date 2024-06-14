package com.humbur.shortdictionary.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val dicId: Int? = null
)
