package com.tzion.corepresentation

import io.reactivex.Observable

interface MviViewModel<TIntent, TUiState> {

    fun processUserIntent(userIntents: Observable<TIntent>)

    fun uiStates(): Observable<TUiState>

}