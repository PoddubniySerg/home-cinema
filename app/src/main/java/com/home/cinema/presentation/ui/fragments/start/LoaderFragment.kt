package com.home.cinema.presentation.ui.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
class LoaderFragment @Inject constructor() : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel>()
    private var binding: LoaderFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoaderFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.stateFlow.onEach { state ->
            when (state) {
                States.COMPLETE -> {
                    delay(1000)
                    findNavController().navigate(R.id.action_loaderFragment_to_homeFragment)
                }
                else -> {
//                    TODO nothing
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getCollections(resources.getStringArray(R.array.home_movie_collections_names))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}