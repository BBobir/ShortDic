package com.humbur.shortdictionary.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humbur.shortdictionary.App
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    val id = savedStateHandle.get<String>("id")


    private val _getWord = MutableLiveData<Dictionary>()
    val getWord: LiveData<Dictionary> = _getWord

    init {
        id?.let { getWordById(it) }
    }

    private fun getWordById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = AssetDatabaseOpenHelper(App.application, "dictionary.db")
            data.saveDatabase()
                .rawQuery("SELECT * FROM `WordsEntity` WHERE `id` = $id", null)
                .use { cursor ->

                    if (cursor.moveToFirst()) {
                        val id = cursor.getInt(0)
                        val eng = cursor.getString(1)
                        val uzb = cursor.getString(2)
                        val short = cursor.getString(3)
                        val type = cursor.getString(4)


                        _getWord.postValue(Dictionary(id = id, eng = eng, uzb = uzb, shortA = short, typeA = type))

                    }
                }
        }

    }



}