package com.tzion.openmovies.presentation.reducer

import com.tzion.mvi.MviReducer
import com.tzion.openmovies.presentation.result.FindMoviesResult
import com.tzion.openmovies.presentation.result.FindMoviesResult.FindMoviesByTextResult.*
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState.*
import javax.inject.Inject

class FindMoviesReducer @Inject constructor() : MviReducer<FindMoviesUiState, FindMoviesResult> {

    override infix fun FindMoviesUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (val previousState = this) {
            is DefaultUiState    -> previousState reduceWith result
            is LoadingUiState    -> previousState reduceWith result
            is EmptyListUiState  -> previousState reduceWith result
            is ShowMoviesUiState -> previousState reduceWith result
            is FailureUiState   -> previousState reduceWith result
        }

    private infix fun DefaultUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            InProcess -> LoadingUiState
            else      -> DefaultUiState
        }

    private infix fun LoadingUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            is Success -> {
                if (result.movies.isEmpty()) {
                    EmptyListUiState
                } else {
                    ShowMoviesUiState(result.movies)
                }
            }
            is Error   -> FailureUiState(result.error)
            else       -> DefaultUiState
        }

    private infix fun EmptyListUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            InProcess -> LoadingUiState
            else      -> DefaultUiState
        }

    private infix fun ShowMoviesUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            InProcess -> LoadingUiState
            else      -> DefaultUiState
        }

    private infix fun FailureUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            is InProcess -> LoadingUiState
            else         -> DefaultUiState
        }

}