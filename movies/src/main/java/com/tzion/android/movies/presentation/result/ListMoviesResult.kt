package com.tzion.android.movies.presentation.result

import com.tzion.android.movies.domain.model.DomainMovie
import com.tzion.android.movies.ui.model.UiMovie

sealed class ListMoviesResult {

    sealed class ShowListEmptyResult: ListMoviesResult() {
        object Success: ShowListEmptyResult()
    }

    sealed class FindMoviesByTextResult: ListMoviesResult() {
        data class Success(val movies: List<DomainMovie>): FindMoviesByTextResult()
        data class Error(val error: Throwable): FindMoviesByTextResult()
        object InFlight: FindMoviesByTextResult()
    }

}