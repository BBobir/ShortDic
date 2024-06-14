package com.humbur.shortdictionary.model

import com.humbur.shortdictionary.local.entity.Favorite

data class Dictionary(
    val id: Int? = null,
    val short: String? = null,
    val full: String? = null,
    val uzb: String? = null,
    var favorite: Boolean = false
)
