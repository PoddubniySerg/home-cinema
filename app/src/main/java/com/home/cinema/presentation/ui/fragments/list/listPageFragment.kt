package com.home.cinema.presentation.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.R
import com.home.cinema.databinding.ListPageFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class listPageFragment : Fragment() {

    private var binding: ListPageFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListPageFragmentBinding.inflate(inflater, container, false)
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

        binding!!.moviesRecyclerView.addItemDecoration(verticalDividerItemDecoration)
        binding!!.moviesRecyclerView.addItemDecoration(horizontalDividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}