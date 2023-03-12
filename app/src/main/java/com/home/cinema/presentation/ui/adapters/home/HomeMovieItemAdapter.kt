package com.home.cinema.presentation.ui.adapters.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.home.cinema.R
import com.home.cinema.databinding.MoviesItemMovieBinding
import com.home.cinema.databinding.MoviesItemShowAllBinding
import com.home.cinema.domain.models.entities.collections.HomeCollection
import com.home.cinema.domain.models.entities.movies.GenreString
import com.home.cinema.domain.models.entities.movies.Movie
import java.util.*

class MovieItemAdapter(
    private val collection: HomeCollection,
    private val onItemPosterClick: (Movie) -> Unit,
    private val onClickAllButton: (HomeCollection) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.movies_item_movie -> {
                return MovieViewHolder(
                    MoviesItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ShowAllViewHolder(
                    MoviesItemShowAllBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> inflateMovie(position, holder.binding)
            is ShowAllViewHolder -> inflateShowAllItem(holder.binding)
        }
    }

    override fun getItemCount(): Int {
        val additionalElementsCount = 1
        return super.getItemCount() + additionalElementsCount
    }

    override fun getItemViewType(position: Int): Int {
        val lastIndexItems = itemCount - 1
        return if (position < lastIndexItems) R.layout.movies_item_movie
        else R.layout.movies_item_show_all
    }

    private fun inflateMovie(position: Int, binding: MoviesItemMovieBinding) {
        val movie = getItem(position)
        with(binding) {

            setSeenIcon(
                seen = movie.seen,
                seenIcon = seenIcon,
                posterView = poster,
                movie = movie
            )

            setRating(
                rating = movie.rating,
                rateView = rating
            )

            setNameMovie(movie, nameMovie)

            setGenres(movie.genres, genresMovie)

            root.setOnClickListener {
                onItemPosterClick(movie)
            }
        }
    }

    private fun inflateShowAllItem(binding: MoviesItemShowAllBinding) {
        with(binding) {
            buttonShowAllMovies.setOnClickListener {
                onClickAllButton(collection)
            }
        }
    }

    private fun setRating(rating: String?, rateView: AppCompatTextView) {
        if (rating == null) rateView.visibility = View.GONE
        else {
            rateView.text = rating
            rateView.visibility = View.VISIBLE
        }
    }

    private fun setSeenIcon(
        seen: Boolean,
        seenIcon: View,
        posterView: AppCompatImageView,
        movie: Movie
    ) {
        val placeHolderId =
            if (seen) {
                seenIcon.visibility = View.VISIBLE
                R.drawable.movies_item_poster_background_place_holder_seen
            } else {
                seenIcon.visibility = View.GONE
                R.drawable.movies_item_poster_background_place_holder_not_seen
            }
        Glide.with(posterView)
            .load(movie.posterUrlPreview ?: return)
            .placeholder(placeHolderId)
            .into(posterView)
    }

    private fun setNameMovie(movie: Movie, nameView: AppCompatTextView) {

        when (Locale.getDefault().language == "ru") {
            true -> {
                val name = movie.nameRu
                nameView.text =
                    if (name == null || name.isEmpty() || name.isBlank()) movie.nameEn ?: ""
                    else name
            }
            false -> {
                val name = movie.nameEn
                nameView.text =
                    if (name == null || name.isEmpty() || name.isBlank()) movie.nameRu ?: ""
                    else name
            }
        }
    }

    private fun setGenres(genres: List<GenreString>?, genreView: AppCompatTextView) {
        if (genres == null) genreView.visibility = View.GONE
        else {
            genreView.text =
                genres.mapNotNull { genre -> genre.genre }.joinToString(", ")
        }
    }
}

class MovieViewHolder(val binding: MoviesItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root)

class ShowAllViewHolder(val binding: MoviesItemShowAllBinding) :
    RecyclerView.ViewHolder(binding.root)

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}