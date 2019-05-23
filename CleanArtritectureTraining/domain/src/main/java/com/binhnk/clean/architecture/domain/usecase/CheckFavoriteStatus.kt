package com.binhnk.clean.architecture.domain.usecase

import com.binhnk.clean.architecture.domain.MoviesCache
import com.binhnk.clean.architecture.domain.common.Transformer
import io.reactivex.Observable

class CheckFavoriteStatus(
    transformer: Transformer<Boolean>,
    private val moviesCache: MoviesCache
) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_MOVIE_ENTITY = "param:movieEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val movieId = data?.get(PARAM_MOVIE_ENTITY)
        movieId?.let {
            return moviesCache.get(it as Int).flatMap { optionalMovieEntity ->
                return@flatMap Observable.just(optionalMovieEntity.hasValue())
            }
        } ?: return Observable.error { IllegalArgumentException("MovieId must be provided.") }
    }

    fun check(movieId: Int): Observable<Boolean> {
        val data = HashMap<String, Int>()
        data[PARAM_MOVIE_ENTITY] = movieId
        return observable(data)
    }

}