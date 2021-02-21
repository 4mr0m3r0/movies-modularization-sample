package com.tzion.openmovies.presentation.userintent

import com.tzion.mvi.events.MviUserIntent

sealed class FindMoviesUserIntent : MviUserIntent {

    data class SearchFilterUserIntent(val queryText: String): FindMoviesUserIntent()

}