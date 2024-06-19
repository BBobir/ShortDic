package com.humbur.shortdictionary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eng: String,
    val uzb: String,
    val shortA: String? = null,
    val typeA: String? = null,
    var favorite: Boolean = false
)