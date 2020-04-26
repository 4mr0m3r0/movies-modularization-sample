package com.tzion.openmovies.presentation.userintent

sealed class FindMoviesUserIntent {

    data class SearchFilterUserIntent(val queryText: String): FindMoviesUserIntent()

}