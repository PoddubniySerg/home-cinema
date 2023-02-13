package com.home.cinema.presentation.ui.adapters

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
import com.home.cinema.databinding.HomeMoviesItemMovieBinding
import com.home.cinema.databinding.HomeMoviesItemShowAllBinding
import com.home.cinema.domain.models.entities.page.home.GenreString
import com.home.cinema.domain.models.entities.page.home.PremierMovie
import java.util.*

class HomeMovieItemAdapter(
    val onItemPosterClick: (PremierMovie) -> Unit,
    private val onClickAllButton: () -> Unit
) : ListAdapter<PremierMovie, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.home_movies_item_movie -> {
                return MovieViewHolder(
                    HomeMoviesItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ShowAllViewHolder(
                    HomeMoviesItemShowAllBinding.inflate(
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
        return if (position < lastIndexItems) R.layout.home_movies_item_movie
        else R.layout.home_movies_item_show_all
    }

    private fun inflateMovie(position: Int, binding: HomeMoviesItemMovieBinding) {
        val movie = getItem(position)
        with(binding) {

            setIsSeenIcon(
                isSeen = movie.isSeen,
                isSeenIcon = isSeenIcon,
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

    private fun inflateShowAllItem(binding: HomeMoviesItemShowAllBinding) {
        with(binding) {
            buttonShowAllMovies.setOnClickListener {
                onClickAllButton.invoke()
            }
        }
    }

    private fun setRating(rating: Double?, rateView: AppCompatTextView) {
        if (rating == null) rateView.visibility = View.GONE
        else {
            rateView.text = String.format("%.1f", rating)
            rateView.visibility = View.VISIBLE
        }
    }
    private fun setIsSeenIcon(
        isSeen: Boolean,
        isSeenIcon: View,
        posterView: AppCompatImageView,
        movie: PremierMovie
    ) {
        val placeHolderId =
            if (isSeen) {
                isSeenIcon.visibility = View.VISIBLE
                R.drawable.home_movie_poster_background_place_holder_seen
            } else {
                isSeenIcon.visibility = View.GONE
                R.drawable.home_movie_poster_background_place_holder_not_seen
            }
        Glide.with(posterView)
            .load(movie.posterUrlPreview ?: return)
            .placeholder(placeHolderId)
            .into(posterView)
    }

    private fun setNameMovie(movie: PremierMovie, nameView: AppCompatTextView) {

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

class MovieViewHolder(val binding: HomeMoviesItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root)

class ShowAllViewHolder(val binding: HomeMoviesItemShowAllBinding) :
    RecyclerView.ViewHolder(binding.root)

class MovieDiffUtilCallback : DiffUtil.ItemCallback<PremierMovie>() {
    override fun areItemsTheSame(oldItem: PremierMovie, newItem: PremierMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PremierMovie, newItem: PremierMovie): Boolean {
        return oldItem.id == newItem.id
    }
}