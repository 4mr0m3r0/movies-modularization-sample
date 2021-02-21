package com.tzion.openmovies.presentation.reducer

import com.tzion.mvi.MviReducer
import com.tzion.openmovies.presentation.result.FindMoviesResult
import com.tzion.openmovies.presentation.result.FindMoviesResult.FindMoviesByTextResult.InProcess
import com.tzion.openmovies.presentation.result.FindMoviesResult.FindMoviesByTextResult.Success
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState.*
import javax.inject.Inject

class FindMoviesReducer @Inject constructor() : MviReducer<FindMoviesUiState, FindMoviesResult> {

    override infix fun FindMoviesUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (val previousState = this) {
            DefaultUiState    -> previousState reduceWith result
            LoadingUiState    -> previousState reduceWith result
            EmptyListUiState  -> previousState reduceWith result
            is SuccessUiState -> previousState reduceWith result
            is ErrorUiState   -> previousState reduceWith result
        }

    private infix fun DefaultUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            InProcess -> LoadingUiState
            else      -> DefaultUiState
        }

    private infix fun LoadingUiState.reduceWith(result: FindMoviesResult): FindMoviesUiState =
        when (result) {
            is Success -> SuccessUiState //TODO: continue here
        }

}