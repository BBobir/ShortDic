package com.humbur.shortdictionary.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.adapter.WordAdapter
import com.humbur.shortdictionary.databinding.FragmentSearch2Binding
import com.humbur.shortdictionary.local.AssetDatabaseOpenHelper
import com.humbur.shortdictionary.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search2) {
    private var _binding: FragmentSearch2Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearch2Binding.bind(view)
        val data = AssetDatabaseOpenHelper(requireContext(), "dictionary.db")

        viewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(data)
        )[SearchViewModel::class.java]

        viewModel.allWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.adapter = null
                }

                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.adapter =
                        WordAdapter(it.data!!, object : WordAdapter.SetOnClickedListener {
                            override fun itemClicked(position: Int) {
                                findNavController().navigate(
                                    R.id.detailFragment,
                                    bundleOf(Pair("id", it.data[position].id.toString()))
                                )
                            }

                            override fun addFavorite(id: Int, state: Boolean) {
                                viewModel.addFavorite(id, state)
                            }

                        })
                }

                Resource.Status.ERROR -> TODO()
            }

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = lifecycleScope.launch {
                    newText.let {
                        delay(500)
                        viewModel.searchWord(it!!)
                    }
                }
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}