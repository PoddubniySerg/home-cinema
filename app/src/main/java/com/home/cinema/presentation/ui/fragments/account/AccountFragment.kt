package com.home.cinema.presentation.ui.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.home.cinema.databinding.AccountFragmentBinding
import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.enums.States
import com.home.cinema.presentation.ui.adapters.AccountCollectionsAdapter
import com.home.cinema.presentation.ui.adapters.MovieItemAdapter
import com.home.cinema.presentation.viewmodels.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val viewModel by viewModels<AccountViewModel>()

    private var binding: AccountFragmentBinding? = null
    private var seenMoviesAdapter: MovieItemAdapter? = null
    private var interestedMoviesAdapter: MovieItemAdapter? = null
    private var collectionsAdapter: AccountCollectionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        seenMoviesAdapter = MovieItemAdapter(
            { movie -> movieItemOnclick(movie) },
            { allMoviesButtonOnClick() }
        )
        interestedMoviesAdapter = MovieItemAdapter(
            { movie -> movieItemOnclick(movie) },
            { allMoviesButtonOnClick() }
        )
        collectionsAdapter = AccountCollectionsAdapter { accountCollection ->
            accountCollectionItemOnclick(accountCollection)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.seenMoviesRecyclerView.adapter = seenMoviesAdapter
        binding!!.wereInterestedRecyclerView.adapter = interestedMoviesAdapter
        binding!!.userCollectionsRecyclerView.adapter = collectionsAdapter

        viewModel.stateFlow.onEach { state ->
            when (state) {
                States.STARTING -> {}
                States.LOADING -> {}
                States.COMPLETE -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.moviesFlow.onEach { movies ->
            binding!!.countSeenMoviesButton.text = "${movies.size}  >"
            binding!!.countWereInterestedMoviesButton.text = "${movies.size}  >"
            seenMoviesAdapter!!.submitList(movies)
            interestedMoviesAdapter!!.submitList(movies)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.collectionsFlow.onEach { collections ->
            collectionsAdapter!!.submitList(collections)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getMovies()
        viewModel.getCollections()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun movieItemOnclick(movie: Movie) {}

    private fun allMoviesButtonOnClick() {}

    private fun accountCollectionItemOnclick(accountCollection: AccountCollection) {}
}