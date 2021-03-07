package com.tzion.openmovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzion.mvi.MviPresentation
import com.tzion.openmovies.presentation.action.FindMoviesAction
import com.tzion.openmovies.presentation.action.FindMoviesAction.FindMoviesByTextAction
import com.tzion.openmovies.presentation.processor.FindMoviesActionProcessor
import com.tzion.openmovies.presentation.reducer.FindMoviesReducer
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState
import com.tzion.openmovies.presentation.userintent.FindMoviesUserIntent
import com.tzion.openmovies.presentation.userintent.FindMoviesUserIntent.SearchFilterUserIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class FindMoviesViewModel @Inject constructor(
    private val processor: FindMoviesActionProcessor,
    private val reducer: FindMoviesReducer
) : ViewModel(), MviPresentation<FindMoviesUserIntent, FindMoviesUiState> {

    private val defaultUiState: FindMoviesUiState = FindMoviesUiState.DefaultUiState
    private val stateFlow: MutableStateFlow<FindMoviesUiState> = MutableStateFlow(defaultUiState)

    override fun processUserIntents(userIntents: Flow<FindMoviesUserIntent>) {
        userIntents
            .buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .scan(defaultUiState) { previousUiState, result ->
                with(reducer) { previousUiState reduceWith result }
            }
            .onEach { uiState -> stateFlow.value = uiState }
            .launchIn(viewModelScope)
    }

    private fun FindMoviesUserIntent.toAction(): FindMoviesAction = when (this) {
        is SearchFilterUserIntent -> FindMoviesByTextAction(queryText)
    }

    override fun uiStates(): StateFlow<FindMoviesUiState> = stateFlow

}