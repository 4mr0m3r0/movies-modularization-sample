package com.tzion.android.core_presentation

import io.reactivex.Observable

interface MviViewModel<TIntent: MviIntent, TUiState: MviUiState> {

    fun processIntent(intents: Observable<TIntent>)

    fun states(): Observable<TUiState>

}