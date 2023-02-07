package com.home.cinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.home.cinema.databinding.HomeMoviesItemCollectionBinding
import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.model.HomeMoviesCollection

class HomeItemMoviesListAdapter(
    private val onItemPosterClick: (Movie) -> Unit,
    private val onClickAllButton: () -> Unit
) : ListAdapter<HomeMoviesCollection, MoviesViewHolder>(MoviesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            HomeMoviesItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        val postersAdapter = HomeMovieItemAdapter(onItemPosterClick, onClickAllButton)
        with(holder.binding) {
            if (item.movies.size < Constants.MAX_MOVIES_COLLECTION_SIZE) {
                buttonAllMovies.visibility = View.GONE
                buttonAllMovies.isActivated = false
            } else {
                buttonAllMovies.visibility = View.VISIBLE
                buttonAllMovies.isActivated = true
                buttonAllMovies.setOnClickListener { onClickAllButton.invoke() }
            }

            nameMovies.text = item.name

            if (moviePosters.adapter == null) moviePosters.adapter = postersAdapter
            postersAdapter.submitList(item.movies)
        }
    }
}

class MoviesViewHolder(val binding: HomeMoviesItemCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class MoviesDiffUtilCallback : DiffUtil.ItemCallback<HomeMoviesCollection>() {
    override fun areItemsTheSame(
        oldItem: HomeMoviesCollection,
        newItem: HomeMoviesCollection
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: HomeMoviesCollection,
        newItem: HomeMoviesCollection
    ): Boolean {
        return oldItem.name == newItem.name
    }

}