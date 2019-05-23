package com.binhnk.clean.architecture.domain

import com.binhnk.clean.architecture.domain.entities.MovieEntity
import com.binhnk.clean.architecture.domain.entities.Optional
import io.reactivex.Observable

interface MoviesRepository {
    fun getMovies(): Observable<List<MovieEntity>>
    fun search(query: String): Observable<List<MovieEntity>>
    fun getMovie(movieId: Int): Observable<Optional<MovieEntity>>
}