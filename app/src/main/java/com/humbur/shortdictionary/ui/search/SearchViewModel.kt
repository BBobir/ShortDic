package com.humbur.shortdictionary.ui.search

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

            val cursor = assetDatabaseOpenHelper.saveDatabase().rawQuery("SELECT * FROM `WordsEntity` ORDER BY `eng` ASC", null)
            while (cursor.moveToNext()) {
                if (cursor.getString(1).isNotEmpty()) {
                    val id = cursor.getInt(0)
                    val eng = cursor.getString(1)
                    val uzb = cursor.getString(2)
                    val short = cursor.getString(3)
                    val type = cursor.getString(4)

                    if (appDatabase.favoiteDao().getFavoriteById(id) == 0) {
                        list.add(Dictionary(id = id, eng = eng, uzb = uzb, shortA = short, typeA = type))
                    } else {
                        list.add(Dictionary(id = id, eng = eng, uzb = uzb, shortA = short, typeA = type, favorite = true))
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
            val cursor = assetDatabaseOpenHelper.saveDatabase().rawQuery("SELECT * FROM `WordsEntity` WHERE `eng` LIKE '%$word%' OR `shortA` LIKE '%$word%'", null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val eng = cursor.getString(1)
                val uzb = cursor.getString(2)
                val short = cursor.getString(3)
                val type = cursor.getString(4)

                if (appDatabase.favoiteDao().getFavoriteById(id) == 0) {
                    list.add(Dictionary(id = id, eng = eng, uzb = uzb, shortA = short, typeA = type))
                } else {
                    list.add(Dictionary(id = id, eng = eng, uzb = uzb, shortA = short, typeA = type, favorite = true))
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