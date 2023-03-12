package com.home.cinema.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.home.cinema.R
import com.home.cinema.databinding.AccountFragmentBinding
import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.enums.States
import com.home.cinema.presentation.ui.adapters.account.AccountCollectionsAdapter
import com.home.cinema.presentation.ui.adapters.account.AccountMoviesAdapter
import com.home.cinema.presentation.viewmodels.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment @Inject constructor() :
    BindFragment<AccountFragmentBinding>(AccountFragmentBinding::inflate) {

    private val viewModel by viewModels<AccountViewModel>()

    private var seenMoviesAdapter: AccountMoviesAdapter? = null
    private var interestedMoviesAdapter: AccountMoviesAdapter? = null
    private var collectionsAdapter: AccountCollectionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        seenMoviesAdapter = AccountMoviesAdapter(
            { movie -> movieItemOnclick(movie) },
            { viewModel.clearSeen() }
        )
        interestedMoviesAdapter = AccountMoviesAdapter(
            { movie -> movieItemOnclick(movie) },
            { viewModel.clearInterested() }
        )
        collectionsAdapter = AccountCollectionsAdapter { accountCollection ->
            accountCollectionItemOnclick(accountCollection)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seenMoviesRecyclerView.adapter = seenMoviesAdapter
        binding.wereInterestedRecyclerView.adapter = interestedMoviesAdapter
        binding.userCollectionsRecyclerView.adapter = collectionsAdapter

        viewModel.stateFlow.onEach { state ->
            when (state) {
                States.START -> {}
                States.LOADING -> {}
                States.COMPLETE -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.interestedFlow.onEach { movies ->
            binding.countWereInterestedMoviesButton.text =
                resources.getString(R.string.account_show_all_button_text, movies.size)
            interestedMoviesAdapter!!.submitList(movies)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.seenFlow.onEach { movies ->
            binding.countSeenMoviesButton.text =
                resources.getString(R.string.account_show_all_button_text, movies.size)
            seenMoviesAdapter!!.submitList(movies)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.collectionsFlow.onEach { collections ->
            collectionsAdapter!!.submitList(collections)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getCollections()
    }

    private fun movieItemOnclick(movie: Movie) {}

    private fun accountCollectionItemOnclick(accountCollection: AccountCollection) {}
}