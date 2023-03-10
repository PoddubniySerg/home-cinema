package com.home.cinema.presentation.ui.adapters.listpage

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
import com.home.cinema.domain.models.entities.movies.GenreString
import com.home.cinema.domain.models.entities.movies.Movie
import java.util.*

class PremiersListPageAdapter(
    private val onItemClick: (Movie?) -> Unit
) : ListAdapter<Movie, PremiersListPageMovieViewHolder>(PremiersListPageMovieDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PremiersListPageMovieViewHolder {
        return PremiersListPageMovieViewHolder(
            MoviesItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PremiersListPageMovieViewHolder, position: Int) {
        val movie = getItem(position)

        with(holder.binding) {

            setSeenIcon(
                seen = movie?.seen ?: false,
                seenIcon = seenIcon,
                posterView = poster,
                movie = movie
            )

            setRating(
                rating = movie?.rating,
                rateView = rating
            )

            setNameMovie(movie, nameMovie)

            setGenres(movie?.genres, genresMovie)

            root.setOnClickListener {
                onItemClick(movie)
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
        movie: Movie?
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
            .load(movie?.posterUrlPreview ?: return)
            .placeholder(placeHolderId)
            .into(posterView)
    }

    private fun setNameMovie(movie: Movie?, nameView: AppCompatTextView) {

        when (Locale.getDefault().language == "ru") {
            true -> {
                val name = movie?.nameRu
                nameView.text =
                    if (name == null || name.isEmpty() || name.isBlank()) movie?.nameEn ?: ""
                    else name
            }
            false -> {
                val name = movie?.nameEn
                nameView.text =
                    if (name == null || name.isEmpty() || name.isBlank()) movie?.nameRu ?: ""
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

class PremiersListPageMovieViewHolder(val binding: MoviesItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root)

class PremiersListPageMovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}