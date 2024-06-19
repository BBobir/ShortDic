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
                    .rawQuery("SELECT * FROM `WordsEntity` WHERE `id` = $id", null)
                    .use { cursor ->

                        if (cursor.moveToFirst()) {
                            val id = cursor.getInt(0)
                            val eng = cursor.getString(1)
                            val uzb = cursor.getString(2)
                            val short = cursor.getString(3)
                            val type = cursor.getString(4)

                            list.add(
                                Dictionary(
                                    id = id,
                                    eng = eng,
                                    uzb = uzb,
                                    shortA = short,
                                    typeA = type,
                                    favorite = true
                                )
                            )
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