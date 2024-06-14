package com.humbur.shortdictionary.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.humbur.shortdictionary.model.FremModel

class HomeViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val list: List<FremModel>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        return list[position].fragment
    }
}