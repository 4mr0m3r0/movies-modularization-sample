package com.tzion.android.core_presentation

import io.reactivex.Observable

interface MviUi<TIntent: MviIntent, in TUiState: MviUiState> {

    fun intents(): Observable<TIntent>

    fun render(state: TUiState)

}