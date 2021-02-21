package com.tzion.mvi

import com.tzion.mvi.events.MviUiState
import com.tzion.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviPresentation<TUserIntent: MviUserIntent, TUiState: MviUiState> {

    fun processUserIntents(userIntents: Flow<TUserIntent>)

    fun uiStates(): StateFlow<TUiState>

}