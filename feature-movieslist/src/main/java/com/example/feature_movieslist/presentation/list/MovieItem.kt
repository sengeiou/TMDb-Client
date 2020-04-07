package com.example.feature_movieslist.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.MovieEntity
import com.example.feature_movieslist.R
import com.example.feature_movieslist.databinding.ItemMovieLineBinding
import com.xwray.groupie.viewbinding.BindableItem

class MovieItem(
    private val movie: MovieEntity,
    private val onMoviewClickListener: (MovieEntity) -> Unit
) : BindableItem<ItemMovieLineBinding>() {

    override fun getLayout() = R.layout.item_movie_line

    override fun initializeViewBinding(view: View): ItemMovieLineBinding {
        return ItemMovieLineBinding.bind(view)
    }

    override fun getId() = movie.id.toLong()

    override fun bind(viewBinding: ItemMovieLineBinding, position: Int) {
        viewBinding.root.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                onMoviewClickListener.invoke(movie)
            }
        }
        viewBinding.movieTitle.text = movie.title
        viewBinding.movieSubtitle.text = movie.subtitle
        viewBinding.movieGenre.text = movie.genre
        viewBinding.movieRating.text = movie.rating.toString()
        viewBinding.movieRatingsCount.text = movie.ratingCount.toString()
        viewBinding.movieDuration.text = movie.duration
        Glide.with(viewBinding.root)
            .load(movie.posterUrl)
            .placeholder(R.drawable.movie_placeholder)
            .into(viewBinding.moviePoster)
    }
}
