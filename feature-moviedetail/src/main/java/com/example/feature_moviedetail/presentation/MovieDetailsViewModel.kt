package com.example.feature_moviedetail.presentation

import androidx.lifecycle.MutableLiveData
import com.example.core.data.account.AccountRepository
import com.example.core.data.movies.MoviesRepository
import com.example.core.domain.MovieEntity
import com.example.core.presentation.BaseViewModel
import com.example.core.rxjava.SchedulersProvider
import com.example.core.util.delegate
import com.example.core.util.ioToMain
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val accountRepository: AccountRepository,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val liveState = MutableLiveData<MovieDetailsViewState>()
    private var state by liveState.delegate()

    fun setMovie(movie: MovieEntity) {
        state = MovieDetailsViewState(movie = movie, isFavorite = false)
        moviesRepository.isMovieFavorite(movie.id)
            .ioToMain(schedulersProvider)
            .subscribeBy(
                onSuccess = { state = state.copy(isFavorite = it) },
                onError = Timber::e
            )
            .let(this::addDisposable)
    }

    fun onAddFavoriteButtonClick() {
        val newIsFavorite = !state.isFavorite
        state = state.copy(isFavorite = newIsFavorite)
        accountRepository.setMovieIsFavorite(
            movieId = state.movie.id,
            isFavorite = newIsFavorite
        )
            .ioToMain(schedulersProvider)
            .subscribeBy(
                onError = { error ->
                    Timber.e(error)
                    state = state.copy(isFavorite = !newIsFavorite)
                }
            )
            .let(this::addDisposable)
    }
}
