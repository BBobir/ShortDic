package com.humbur.shortdictionary.model

data class Dictionary(
    var id: Int = 0,
    var eng: String,
    var uzb: String,
    var shortA: String? = null,
    var typeA: String? = null,
    var favorite: Boolean = false
)
