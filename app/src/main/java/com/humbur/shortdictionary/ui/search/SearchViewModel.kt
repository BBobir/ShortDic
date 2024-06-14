package com.humbur.shortdictionary.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humbur.shortdictionary.local.AppDatabase
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.local.entity.Favorite
import com.humbur.shortdictionary.model.Dictionary
import com.humbur.shortdictionary.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val assetDatabaseOpenHelper: AssetDatabaseOpenHelper) : ViewModel() {

    private val _allWords = MutableLiveData<Resource<List<Dictionary>>>()
    val allWords: LiveData<Resource<List<Dictionary>>> = _allWords

    init {
        getWords()
    }

    private fun getWords() {
        _allWords.postValue(Resource.loading(null))
        val list = ArrayList<Dictionary>()
        val appDatabase = AppDatabase.getDatabase()
        viewModelScope.launch(Dispatchers.IO) {
            //val data = AssetDatabaseOpenHelper(context, "database.db")
            val cursor = assetDatabaseOpenHelper.saveDatabase().rawQuery("SELECT * FROM `abbreviations`", null)
            while (cursor.moveToNext()) {
                if (cursor.getString(1).isNotEmpty()) {
                    val id = cursor.getInt(0)
                    val short = cursor.getString(1)
                    val full = cursor.getString(2)
                    val uzb = cursor.getString(3)

                    if (appDatabase.favoiteDao().getFavoriteById(id) == 0) {
                        list.add(Dictionary(id, short, full, uzb))
                    } else {
                        list.add(Dictionary(id, short, full, uzb, true))
                    }
                }
            }
            _allWords.postValue(Resource.success(list))
        }
    }

    fun searchWord(word: String) {
        _allWords.postValue(Resource.loading(null))
        val list = ArrayList<Dictionary>()
        val appDatabase = AppDatabase.getDatabase()
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = assetDatabaseOpenHelper.saveDatabase().rawQuery("SELECT * FROM `abbreviations` WHERE `abbr_abbreviation` LIKE '%$word%' OR `abbr_extension` LIKE '%$word%'", null)
            while (cursor.moveToNext()) {
                if (cursor.getString(1).isNotEmpty()) {
                    val id = cursor.getInt(0)
                    val short = cursor.getString(1)
                    val full = cursor.getString(2)
                    val uzb = cursor.getString(3)

                    if (appDatabase.favoiteDao().getFavoriteById(id) == 0) {
                        list.add(Dictionary(id, short, full, uzb))
                    } else {
                        list.add(Dictionary(id, short, full, uzb, true))
                    }
                }
            }
            _allWords.postValue(Resource.success(list))
        }
    }

    fun addFavorite(id: Int, state: Boolean) {

        val appDatabase = AppDatabase.getDatabase()
        viewModelScope.launch(Dispatchers.IO) {
            if (!state)
                appDatabase.favoiteDao().deleteFavorite(id)
            else
            appDatabase.favoiteDao().insertFavorite(Favorite(dicId = id))
        }
    }


}