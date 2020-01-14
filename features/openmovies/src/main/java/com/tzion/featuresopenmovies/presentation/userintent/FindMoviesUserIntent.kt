package com.tzion.featuresopenmovies.presentation.userintent

sealed class FindMoviesUserIntent {

    data class SearchFilterUserIntent(val queryText: String): FindMoviesUserIntent()

}