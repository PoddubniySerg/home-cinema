package com.home.cinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.home.cinema.R
import com.home.cinema.databinding.HomeMovieItemBinding
import com.home.cinema.domain.models.entities.page.home.GenreString
import com.home.cinema.domain.models.entities.page.home.Movie
import java.util.*

class HomeMovieItemAdapter(
    val onItemPosterClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            HomeMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            Glide.with(poster).load(item.posterUrlPreview ?: return@with).into(poster)

            setIsSeenIcon(
                isSeen = item.isSeen,
                isSeenIcon = isSeenIcon,
                posterView = poster
            )

            setRating(
                rating = item.rating?.toString(),
                rateView = rating
            )

            setNameMovie(item, nameMovie)

            setGenres(item.genres, genresMovie)

            root.setOnClickListener {
                item?.let {
                    onItemPosterClick(item)
                }
            }
        }
    }

    private fun setRating(rating: String?, rateView: AppCompatTextView) {
        if (rating == null) rateView.visibility = View.GONE
        else {
            rateView.text = rating.substring(
                startIndex = 0,
                endIndex = rating.indexOf(char = '.', ignoreCase = true) + 1
            )
            rateView.visibility = View.VISIBLE
        }
    }

    private fun setIsSeenIcon(isSeen: Boolean, isSeenIcon: View, posterView: View) {
        when (isSeen) {
            true -> {
                posterView.setBackgroundResource(R.drawable.home_movie_poster_background_seen_layer_list)
                isSeenIcon.visibility = View.VISIBLE
            }
            false -> {
                posterView.setBackgroundResource(R.color.home_movie_poster_not_seen_background_color)
                isSeenIcon.visibility = View.GONE
            }
        }
    }

    private fun setNameMovie(movie: Movie, nameView: AppCompatTextView) {
        nameView.text =
            if (Locale.getDefault().language == "ru") movie.nameRu ?: movie.nameEn ?: "Unknown name"
            else movie.nameEn ?: "Unknown name"
    }

    private fun setGenres(genres: List<GenreString>?, genreView: AppCompatTextView) {
        if (genres == null) genreView.visibility = View.GONE
        else {
            genreView.text =
                genres.mapNotNull { genre -> genre.genre }.joinToString(", ")
        }
    }
}

class MovieViewHolder(val binding: HomeMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}