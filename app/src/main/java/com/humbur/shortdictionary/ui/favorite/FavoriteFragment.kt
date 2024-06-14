package com.humbur.shortdictionary.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.adapter.FavoriteWordAdapter
import com.humbur.shortdictionary.databinding.FragmentFavoriteBinding
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.model.Dictionary
import com.humbur.shortdictionary.utils.Resource

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel

    private val mlist:MutableList<Dictionary> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
        val data = AssetDatabaseOpenHelper(requireContext(), "dictionary.db")

        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(data)
        )[FavoriteViewModel::class.java]


        viewModel.favoriteWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.adapter = null
                }

                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    mlist.clear()
                    mlist.addAll(it.data!!)
                    binding.recyclerView.adapter =
                        FavoriteWordAdapter(mlist, object : FavoriteWordAdapter.SetOnClickedListener {
                            override fun itemClicked(position: Int) {
                                findNavController().navigate(
                                    R.id.detailFragment,
                                    bundleOf(Pair("id", it.data[position].id.toString()))
                                )
                            }

                            override fun deleteFavorite(id: Int, position: Int) {
                                viewModel.deleteFavorite(id)
                            }
                        })
                }

                Resource.Status.ERROR -> TODO()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteWords()
    }

    override fun onPause() {
        super.onPause()
        binding.recyclerView.adapter = null
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}