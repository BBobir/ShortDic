package com.humbur.shortdictionary.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentSearch2Binding
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search2) {
    private var _binding: FragmentSearch2Binding? = null
    private val binding get() = _binding
    private var list: MutableList<Dictionary>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearch2Binding.bind(view)
        list = ArrayList<Dictionary>()



            val data = AssetDatabaseOpenHelper(requireContext(), "database.db")
            val cursor = data.saveDatabase().rawQuery("SELECT * FROM `short_dictionary`", null)
            while (cursor.moveToNext()) {
                if (cursor.getString(1).isNotEmpty()){
                    val id = cursor.getInt(0)
                    val short = cursor.getString(1)
                    val full = cursor.getString(2)
                    val uzb = cursor.getString(3)
                    list?.add(Dictionary(id, short, full, uzb))
                }
            }
            binding?.recyclerView?.adapter = Adapter(list!!,object:Adapter.SetOnClickedListener {
                override fun itemClicked(position: Int) {

                }
            })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}