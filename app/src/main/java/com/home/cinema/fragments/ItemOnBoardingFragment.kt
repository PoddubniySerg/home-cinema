package com.home.cinema.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.home.cinema.databinding.FragmentOnBoardingItemBinding
import com.home.cinema.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemOnBoardingFragment @Inject constructor() : Fragment() {

    companion object {
        private const val IMAGE_ID_KEY = "image_id"
        private const val MESSAGE_KEY = "message"
        private const val IS_LAST_PAGE_KEY = "is_last_page"
        private const val MIN_DIFF_SWIPE = 25f
        private const val START_X_SWIPE = -1f

        fun getInstance(
            @DrawableRes posterId: Int,
            message: String,
            isLastPage: Boolean
        ): ItemOnBoardingFragment {
            val fragment = ItemOnBoardingFragment()
            val args = Bundle().apply {
                putInt(IMAGE_ID_KEY, posterId)
                putString(MESSAGE_KEY, message)
                putBoolean(IS_LAST_PAGE_KEY, isLastPage)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel by activityViewModels<OnBoardingViewModel>()
    private var binding: FragmentOnBoardingItemBinding? = null
    private var startX = START_X_SWIPE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingItemBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getBoolean(IS_LAST_PAGE_KEY) == true) setLeftSwipeListener()
        binding?.poster?.setImageResource(requireArguments().getInt(IMAGE_ID_KEY))
        binding?.message?.text = requireArguments().getString(MESSAGE_KEY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setLeftSwipeListener() {
        binding!!.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    if (startX >= 0 && (startX - event.x) > MIN_DIFF_SWIPE) {
                        viewModel.exit()
                    }
                }
            }
            return@setOnTouchListener false
        }
    }
}