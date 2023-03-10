package com.home.cinema.presentation.ui.adapters.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.R
import com.home.cinema.databinding.MoviesItemCollectionBinding
import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.collections.HomeCollection
import com.home.cinema.domain.models.entities.movies.Movie

class MoviesCollectionsAdapter(
    private val onItemPosterClick: (Movie) -> Unit,
    private val onClickAllButton: (HomeCollection) -> Unit
) : ListAdapter<HomeCollection, MoviesViewHolder>(MoviesDiffUtilCallback()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MoviesItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        val postersAdapter = MovieItemAdapter(item, onItemPosterClick, onClickAllButton)
        with(holder.binding) {
            if (item.movies.size < Constants.MAX_MOVIES_COLLECTION_SIZE) {
                buttonAllMovies.visibility = View.GONE
                buttonAllMovies.isActivated = false
            } else {
                buttonAllMovies.visibility = View.VISIBLE
                buttonAllMovies.isActivated = true
                buttonAllMovies.setOnClickListener { onClickAllButton(item) }
            }

            val lastIndexItems = itemCount - 1
            if (position == lastIndexItems) {
                val marginBottomDp = 74
                val params = root.layoutParams as RecyclerView.LayoutParams
                params.bottomMargin =
                    marginBottomDp * root.context.resources.displayMetrics.density.toInt()
                root.layoutParams = params
            }

            nameMovies.text = item.name

            moviePosters.adapter = postersAdapter
            val verticalDividerItemDecoration =
                DividerItemDecoration(moviePosters.context, RecyclerView.HORIZONTAL)
            verticalDividerItemDecoration.setDrawable(
                ResourcesCompat.getDrawable(
                    moviePosters.resources,
                    R.drawable.movies_item_divider,
                    moviePosters.context.theme
                )!!
            )
            moviePosters.addItemDecoration(verticalDividerItemDecoration)
            moviePosters.setRecycledViewPool(viewPool)
            postersAdapter.submitList(item.movies)
        }
    }
}

class MoviesViewHolder(val binding: MoviesItemCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class MoviesDiffUtilCallback : DiffUtil.ItemCallback<HomeCollection>() {
    override fun areItemsTheSame(
        oldItem: HomeCollection,
        newItem: HomeCollection
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: HomeCollection,
        newItem: HomeCollection
    ): Boolean {
        return oldItem.name == newItem.name
    }

}