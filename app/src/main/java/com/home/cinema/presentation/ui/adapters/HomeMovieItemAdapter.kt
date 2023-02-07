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
import com.home.cinema.databinding.HomeMoviesItemMovieBinding
import com.home.cinema.databinding.HomeMoviesItemShowAllBinding
import com.home.cinema.domain.models.entities.page.home.GenreString
import com.home.cinema.domain.models.entities.page.home.Movie
import java.util.*

class HomeMovieItemAdapter(
    val onItemPosterClick: (Movie) -> Unit,
    private val onClickAllButton: () -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

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
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) R.layout.home_movies_item_movie
        else R.layout.home_movies_item_show_all
    }

    private fun inflateMovie(position: Int, binding: HomeMoviesItemMovieBinding) {
        val movie = getItem(position)
        with(binding) {
            Glide.with(poster).load(movie.posterUrlPreview ?: return@with).into(poster)

            setIsSeenIcon(
                isSeen = movie.isSeen,
                isSeenIcon = isSeenIcon,
                posterView = poster
            )

            setRating(
                rating = movie.rating?.toString(),
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

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}