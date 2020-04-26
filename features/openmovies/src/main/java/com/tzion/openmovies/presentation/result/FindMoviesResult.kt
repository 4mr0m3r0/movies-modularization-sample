package com.tzion.openmovies.presentation.result

import com.tzion.openmovies.domain.model.DomainMovie

sealed class FindMoviesResult {

    sealed class FindMoviesByTextResult: FindMoviesResult() {
        data class Success(val movies: List<DomainMovie>): FindMoviesByTextResult()
        data class Error(val error: Throwable): FindMoviesByTextResult()
        object InProcess: FindMoviesByTextResult()
    }

}