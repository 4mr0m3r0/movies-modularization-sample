package com.tzion.featuresopenmovies.presentation.result

import com.tzion.featuresopenmovies.domain.model.DomainMovie

sealed class FindMoviesResult {

    sealed class FindMoviesByTextResult: FindMoviesResult() {
        data class Success(val movies: List<DomainMovie>): FindMoviesByTextResult()
        data class Error(val error: Throwable): FindMoviesByTextResult()
        object InProcess: FindMoviesByTextResult()
    }

}