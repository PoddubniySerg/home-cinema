package com.home.cinema.presentation.ui.fragments.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.home.cinema.R
import com.home.cinema.databinding.OnboardingFragmentBinding
import com.home.cinema.presentation.ui.fragments.BindFragment
import com.home.cinema.presentation.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment @Inject constructor() :
    BindFragment<OnboardingFragmentBinding>(OnboardingFragmentBinding::inflate) {

    private val viewModel by activityViewModels<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.exitFromOnBoardingFlow.onEach { onBoardingIsFinished ->
            if (onBoardingIsFinished)
                findNavController().navigate(R.id.action_onBoardingFragment_to_loaderFragment)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.isFirstAppLaunchFlow.onEach { isFirstAppLaunch ->
            if (!isFirstAppLaunch) viewModel.exit()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        runBlocking {
            viewModel.isFirstLaunch()
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.skipButton.setOnClickListener {
            viewModel.exit()
        }

        viewPagerInit()
    }

    private fun viewPagerInit() {
        val viewPager = binding.viewPager
        val adapter = OnBoardingAdapter(fragment = this)
        viewPager.adapter = adapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            tab.view.background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.on_boarding_dots_selector,
                    null
                )
        }.attach()
    }

    @SuppressLint("DiscouragedApi")
    private class OnBoardingAdapter(
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        private val items: List<OnBoardingItem>

        init {
            val onBoardingItems = mutableListOf<OnBoardingItem>()
            val resources = fragment.resources
            val messages = resources.getStringArray(R.array.on_boarding_messages)
            for (i in messages.indices) {
                onBoardingItems.add(
                    i,
                    OnBoardingItem(
                        resources
                            .getIdentifier(
                                "on_boarding_poster_page_${i + 1}",
                                "drawable",
                                fragment.requireContext().packageName
                            ),
                        messages[i]
                    )
                )
            }
            this.items = onBoardingItems
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            return ItemOnboardingFragment.getInstance(
                items[position].posterId,
                items[position].message,
                isLastPage = position == items.size - 1
            )
        }
    }

    private class OnBoardingItem(
        @DrawableRes val posterId: Int,
        val message: String
    )
}