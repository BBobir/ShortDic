package com.humbur.shortdictionary.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.humbur.shortdictionary.App
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary

class DetailViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    val id = savedStateHandle.get<String>("id")


    private val _getWord = MutableLiveData<Dictionary>()
    val getWord: LiveData<Dictionary> = _getWord

    init {
        id?.let { getWordById(it) }
    }

    private fun getWordById(id: String) {
        val data = AssetDatabaseOpenHelper(App.application, "dictionary.db")
        data.saveDatabase()
            .rawQuery("SELECT * FROM `abbreviations` WHERE `abbr_id` = $id", null)
            .use { cursor ->

                if (cursor.moveToFirst()) {
                    if (cursor.getString(1).isNotEmpty()) {
                        val ido = cursor.getInt(0)
                        val short = cursor.getString(1)
                        val full = cursor.getString(2)
                        val uzb = cursor.getString(3)
                        _getWord.value = Dictionary(ido, short, full, uzb, true)
                    }
                }
            }


    }



}