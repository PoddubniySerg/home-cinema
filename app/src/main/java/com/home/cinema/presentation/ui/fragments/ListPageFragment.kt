package com.home.cinema.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.R
import com.home.cinema.databinding.ListPageFragmentBinding
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.enums.States
import com.home.cinema.presentation.ui.adapters.ListPageAdapter
import com.home.cinema.presentation.viewmodels.ListPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListPageFragment : Fragment() {

    private val viewModel by activityViewModels<ListPageViewModel>()
    private var binding: ListPageFragmentBinding? = null
    private lateinit var adapter: ListPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListPageFragmentBinding.inflate(inflater, container, false)
        adapter = ListPageAdapter { movie -> onItemClick(movie) }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initSwipeRefresh()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initToolbar() {
        val toolbar = binding!!.listPageToolbar
        toolbar.setupWithNavController(findNavController())
        toolbar.title = null
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_icon)
        binding?.listPageToolbarTitle?.text = viewModel.collectionName
    }

    private fun initRecyclerView() {
        val verticalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        verticalDividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.account_item_collection_divider_vertical,
                requireContext().theme
            )!!
        )

        val horizontalDividerItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
        horizontalDividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.account_item_collection_divider_horizontal,
                requireContext().theme
            )!!
        )

        val recyclerView = binding!!.moviesRecyclerView
        recyclerView.addItemDecoration(verticalDividerItemDecoration)
        recyclerView.addItemDecoration(horizontalDividerItemDecoration)
        recyclerView.adapter = adapter
    }

    private fun initSwipeRefresh() {
        val swipeRefresh = binding!!.swipeRefresh
        swipeRefresh.setColorSchemeResources(R.color.progress_bar_color)
        swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }



    private fun initObservers() {
        viewModel.pagedMovies.onEach { pagingData ->
            adapter.submitData(pagingData)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.loadStateFlow.onEach {
            val state = it.refresh
            binding!!.swipeRefresh.isRefreshing = state == LoadState.Loading
            when (state) {
                is LoadState.Error -> {
                    Toast.makeText(activity, state.error.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
//                    TODO nothing
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.stateFlow.onEach { state ->
            if (state == States.LOADING) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(movie: Movie?) {}
}