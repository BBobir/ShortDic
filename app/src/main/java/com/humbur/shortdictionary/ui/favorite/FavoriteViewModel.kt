package com.humbur.shortdictionary.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humbur.shortdictionary.local.AppDatabase
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary
import com.humbur.shortdictionary.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val assetDatabaseOpenHelper: AssetDatabaseOpenHelper) :
    ViewModel() {

    private val _favoriteWords = MutableLiveData<Resource<List<Dictionary>>>()
    val favoriteWords: MutableLiveData<Resource<List<Dictionary>>> = _favoriteWords

    fun getFavoriteWords() {
        _favoriteWords.value = Resource.loading(null)
        val list = ArrayList<Dictionary>()
        val appDatabase = AppDatabase.getDatabase()
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.favoiteDao().getAllFavorite().forEach {
                val id = it.dicId
                assetDatabaseOpenHelper.saveDatabase()
                    .rawQuery("SELECT * FROM `abbreviations` WHERE `abbr_id` = $id", null)
                    .use { cursor ->

                        if (cursor.moveToFirst()) {
                            if (cursor.getString(1).isNotEmpty()) {
                                val ido = cursor.getInt(0)
                                val short = cursor.getString(1)
                                val full = cursor.getString(2)
                                val uzb = cursor.getString(3)
                                list.add(Dictionary(ido, short, full, uzb, true))
                            }
                        }
                    }


            }
            _favoriteWords.postValue(Resource.success(list))
        }

    }


    fun deleteFavorite(id: Int) {
        val appDatabase = AppDatabase.getDatabase()
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.favoiteDao().deleteFavorite(id)
        }

    }


}