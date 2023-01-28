package com.home.cinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.home.cinema.databinding.FragmentItemOnBoardingBinding

class ItemOnBoardingFragment : Fragment() {

    companion object {
        private const val IMAGE_ID_KEY = "image_id"
        private const val MESSAGE_ID_KEY = "message"

        fun getInstance(
            @DrawableRes posterId: Int,
            @StringRes message: Int
        ): ItemOnBoardingFragment {
            val fragment = ItemOnBoardingFragment()
            val args = Bundle().apply {
                putInt(IMAGE_ID_KEY, posterId)
                putInt(MESSAGE_ID_KEY, message)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var binding: FragmentItemOnBoardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemOnBoardingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.poster?.setImageResource(requireArguments().getInt(IMAGE_ID_KEY))
        binding?.message?.text = getString(requireArguments().getInt(MESSAGE_ID_KEY))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}