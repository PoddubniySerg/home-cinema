package com.home.cinema.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.home.cinema.R
import com.home.cinema.databinding.HomeFragmentBinding
import com.home.cinema.domain.models.entities.collections.HomeCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.presentation.ui.adapters.home.MoviesCollectionsAdapter
import com.home.cinema.presentation.viewmodels.HomeViewModel
import com.home.cinema.presentation.viewmodels.ListPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    BindFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val listViewModel by activityViewModels<ListPageViewModel>()
    private var adapter: MoviesCollectionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MoviesCollectionsAdapter(this::onMovieClick, this::onClickAllButton)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesList.adapter = adapter

        homeViewModel.collectionsFlow.onEach {
            adapter?.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        listViewModel.collectionTypeFlow.onEach {
            findNavController().navigate(R.id.action_homeFragment_to_listPageFragment)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onMovieClick(movie: Movie) {}

    private fun onClickAllButton(collection: HomeCollection) {
        listViewModel.openCollection(collection.type, collection.name)
    }
}