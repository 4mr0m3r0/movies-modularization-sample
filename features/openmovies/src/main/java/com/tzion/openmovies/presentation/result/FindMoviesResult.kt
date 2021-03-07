package com.tzion.openmovies.presentation.result

import com.tzion.mvi.events.MviResult
import com.tzion.openmovies.presentation.model.UiMovie

sealed class FindMoviesResult : MviResult {

    sealed class FindMoviesByTextResult: FindMoviesResult() {
        data class Success(val movies: List<UiMovie>): FindMoviesByTextResult()
        data class Error(val error: Throwable): FindMoviesByTextResult()
        object InProcess: FindMoviesByTextResult()
    }

}