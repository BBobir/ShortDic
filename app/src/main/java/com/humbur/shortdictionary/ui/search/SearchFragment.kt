package com.humbur.shortdictionary.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentSearch2Binding
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary


class SearchFragment : Fragment(R.layout.fragment_search2) {
    private var _binding: FragmentSearch2Binding? = null
    private val binding get() = _binding
    private var list: MutableList<Dictionary>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearch2Binding.bind(view)
        list = ArrayList<Dictionary>()


        val data = AssetDatabaseOpenHelper(requireContext(), "database/dictionary.db")
//        val cursor = data.saveDatabase().rawQuery("SELECT * FROM `dictionary`", null)
//        while (cursor.moveToNext()) {
//            val id = cursor.getInt(1)
//            val short = cursor.getString(2)
//            val full = cursor.getString(3)
//            val uzb = cursor.getString(4)
//            val dictionary = Dictionary(id, short, full, uzb)
//            (list as ArrayList<Dictionary>).add(dictionary)
//        }
//        binding?.recyclerView?.adapter = Adapter(list!!,object:Adapter.SetOnClickedListener{
//            override fun itemClicked(position: Int) {
//
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}