package com.humbur.shortdictionary.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.FragmentDetailBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}