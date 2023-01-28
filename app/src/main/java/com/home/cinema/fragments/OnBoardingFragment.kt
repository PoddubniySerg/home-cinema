package com.home.cinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.home.cinema.R

class OnBoardingFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.viewPager)
        adapter = OnBoardingAdapter(fragment = this)
        viewPager.adapter = adapter
    }
}

class OnBoardingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val items: List<OnBoardingItem> = listOf(
        OnBoardingItem(R.drawable.first_on_boarding_poster, R.string.message1),
        OnBoardingItem(R.drawable.second_on_boarding_poster, R.string.message2),
        OnBoardingItem(R.drawable.third_on_boarding_poster, R.string.message3)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return ItemOnBoardingFragment.getInstance(
            items[position].posterId,
            items[position].messageId
        )
    }
}

data class OnBoardingItem(
    @DrawableRes val posterId: Int,
    @StringRes val messageId: Int
)