package com.tzion.mvi

import com.tzion.mvi.events.MviResult
import com.tzion.mvi.events.MviUiState

interface MviReducer<TUiState : MviUiState, TResult: MviResult> {

    infix fun TUiState.reduceWith(result: TResult): TUiState

}