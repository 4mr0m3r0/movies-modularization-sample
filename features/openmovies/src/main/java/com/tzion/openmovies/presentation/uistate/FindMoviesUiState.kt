package com.tzion.openmovies.presentation.uistate

import com.tzion.mvi.events.MviUiState
import com.tzion.openmovies.presentation.model.UiMovie

sealed class FindMoviesUiState(
    val isLoading: Boolean,
    val withSearchInstructions: Boolean,
    val thereAreNotMoviesMatches: Boolean,
    val movies: List<UiMovie>,
    val withError: Boolean,
    val errorMessage: String
) : MviUiState {

    companion object {
        const val DEFAULT_ERROR_MSG = ""
    }

    object DefaultUiState : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = true,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    object LoadingUiState : FindMoviesUiState(
        isLoading = true,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    object EmptyListUiState : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = true,
        movies = emptyList(),
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    data class SuccessUiState(private val presentationMovies: List<UiMovie>) : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = presentationMovies,
        withError = false,
        errorMessage = DEFAULT_ERROR_MSG
    )

    data class ErrorUiState(private val error: String) : FindMoviesUiState(
        isLoading = false,
        withSearchInstructions = false,
        thereAreNotMoviesMatches = false,
        movies = emptyList(),
        withError = true,
        errorMessage = error
    )

}