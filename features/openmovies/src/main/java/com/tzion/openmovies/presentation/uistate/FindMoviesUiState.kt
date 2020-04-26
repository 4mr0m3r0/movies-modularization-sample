package com.tzion.openmovies.presentation.uistate

import com.tzion.openmovies.presentation.model.UiMovie

sealed class FindMoviesUiState(val isLoading: Boolean,
                               val withSearchInstructions: Boolean,
                               val thereAreNotMoviesMatches: Boolean,
                               val movies: List<UiMovie>,
                               val withError: Boolean,
                               val errorMessage: String) {
    companion object {
        const val DEFAULT_ERROR_MSG = ""
    }

    object Default : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = true,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    object Loading : FindMoviesUiState(
        isLoading = true,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    object EmptyList : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = true,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    data class Success(private val presentationMovies: List<UiMovie>) : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = presentationMovies,
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    data class Error(private val error: String) : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = true,
        errorMessage = error
    )

}