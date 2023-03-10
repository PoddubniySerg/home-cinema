package com.home.cinema.presentation.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.R
import com.home.cinema.databinding.ListPageFragmentBinding
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.presentation.ui.adapters.ListPageAdapter
import com.home.cinema.presentation.viewmodels.ListPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListPageFragment : Fragment() {

    private val viewModel by viewModels<ListPageViewModel>()
    private var binding: ListPageFragmentBinding? = null
    private var adapter: ListPageAdapter? = null

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
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(verticalDividerItemDecoration)
        recyclerView.addItemDecoration(horizontalDividerItemDecoration)

        viewModel.pagedMovies.onEach { pagingData ->
            adapter!!.submitData(pagingData)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onItemClick(movie: Movie?) {}
}