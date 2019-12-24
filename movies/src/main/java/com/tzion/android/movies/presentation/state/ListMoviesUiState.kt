package com.tzion.android.movies.presentation.state

import com.tzion.android.core_presentation.MviUiState
import com.tzion.android.movies.ui.model.UiMovie

data class ListMoviesUiState(val isLoading: Boolean,
                             val movies: List<UiMovie>,
                             val error: Throwable?): MviUiState {
    companion object Factory {
        fun default() = ListMoviesUiState(
            isLoading = false,
            movies = emptyList(),
            error = null
        )
    }
}