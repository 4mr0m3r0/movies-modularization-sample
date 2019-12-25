package com.tzion.android.movies.presentation.userintent

sealed class FindMoviesUserIntent {

    data class SearchFilterIntent(val queryText: String): FindMoviesUserIntent()

}