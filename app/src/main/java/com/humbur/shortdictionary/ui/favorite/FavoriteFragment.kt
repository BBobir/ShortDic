package com.humbur.shortdictionary.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}