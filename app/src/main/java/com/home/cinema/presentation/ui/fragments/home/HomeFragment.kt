package com.home.cinema.presentation.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.home.cinema.databinding.HomeFragmentBinding
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.presentation.ui.adapters.HomeItemMoviesListAdapter
import com.home.cinema.presentation.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel>()
    private var binding: HomeFragmentBinding? = null
    private var adapter: HomeItemMoviesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        adapter = HomeItemMoviesListAdapter(
            { movie -> onMovieClick(movie) },
            { onClickAllButton() }
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.moviesList.adapter = adapter

        viewModel.collectionsFlow.onEach {
            adapter?.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onMovieClick(movie: Movie) {}

    private fun onClickAllButton() {}
}