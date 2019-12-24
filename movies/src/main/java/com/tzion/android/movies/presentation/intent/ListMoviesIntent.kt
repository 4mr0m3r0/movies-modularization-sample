package com.tzion.android.movies.presentation.intent

import com.tzion.android.core_presentation.MviIntent

sealed class ListMoviesIntent: MviIntent {

    object InitialIntent: ListMoviesIntent()
    data class SearchFilterIntent(val queryText: String): ListMoviesIntent()

}