package com.humbur.shortdictionary.ui.search

import android.content.Context
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import kotlinx.coroutines.flow.flow

class SearchRepository(private val context: Context) {


    fun getAllData() = flow {
        val data = AssetDatabaseOpenHelper(context, "dictionary.db")
        val newData = data.saveDatabase()
        emit(newData)
    }
}