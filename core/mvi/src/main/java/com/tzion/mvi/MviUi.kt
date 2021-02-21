package com.tzion.mvi

import com.tzion.mvi.events.MviUiState
import com.tzion.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.Flow

interface MviUi<TUserIntent: MviUserIntent, in TUiState: MviUiState> {

    fun userIntents(): Flow<TUserIntent>

    fun renderUiStates(uiState: TUiState)

}