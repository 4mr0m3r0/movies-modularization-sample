package com.tzion.mvi

import io.reactivex.Observable

interface MviPresentation <TIntent, TUiState> {

    fun processUserIntents(userIntents: Observable<TIntent>)

    fun uiStates(): Observable<TUiState>

}