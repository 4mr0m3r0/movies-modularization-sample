package com.tzion.openmovies.presentation.uistate

import com.tzion.mvi.events.MviUiState
import com.tzion.openmovies.presentation.model.UiMovie

sealed class FindMoviesUiState() : MviUiState {

    object DefaultUiState : FindMoviesUiState() // withSearchInstructions = true

    object LoadingUiState : FindMoviesUiState()

    object EmptyListUiState : FindMoviesUiState()

    data class ShowMoviesUiState(val movies: List<UiMovie>) : FindMoviesUiState()

    data class FailureUiState(val error: Throwable) : FindMoviesUiState()

}