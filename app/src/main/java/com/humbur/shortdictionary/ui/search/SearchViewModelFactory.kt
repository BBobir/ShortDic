package com.humbur.shortdictionary.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper

class SearchViewModelFactory(private val assetDatabaseOpenHelper: AssetDatabaseOpenHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(assetDatabaseOpenHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}