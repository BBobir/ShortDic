package com.humbur.shortdictionary.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.adapter.HomeViewPagerAdapter
import com.humbur.shortdictionary.databinding.FragmentHomeBinding
import com.humbur.shortdictionary.model.FremModel
import com.humbur.shortdictionary.ui.favorite.FavoriteFragment
import com.humbur.shortdictionary.ui.search.SearchFragment


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        val list:MutableList<FremModel> = ArrayList()
        list.add(FremModel("Search", SearchFragment()))
        list.add(FremModel("Favorite", FavoriteFragment()))

        binding.vpHome.adapter = HomeViewPagerAdapter(childFragmentManager, lifecycle, list)
        TabLayoutMediator(
            binding.tables, binding.vpHome
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = list[position].name
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}