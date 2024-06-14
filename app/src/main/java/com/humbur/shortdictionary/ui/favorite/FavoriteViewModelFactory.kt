package com.humbur.shortdictionary.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper

class FavoriteViewModelFactory(private val assetDatabaseOpenHelper: AssetDatabaseOpenHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(assetDatabaseOpenHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}