package com.home.cinema.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.home.cinema.R
import com.home.cinema.databinding.LoaderFragmentBinding
import com.home.cinema.enums.States
import com.home.cinema.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoaderFragment @Inject constructor() :
    BindFragment<LoaderFragmentBinding>(LoaderFragmentBinding::inflate) {

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCollections(resources.getStringArray(R.array.home_movie_collections_names))
    }

    override fun onResume() {
        super.onResume()

        viewModel.stateFlow.onEach { state ->
            when (state) {
                States.COMPLETE -> {
                    delay(500)
                    findNavController().navigate(R.id.action_loaderFragment_to_homeFragment)
                }
                else -> {
//                    TODO nothing
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}