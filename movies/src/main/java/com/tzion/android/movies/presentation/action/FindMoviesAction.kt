package com.tzion.android.movies.presentation.action

sealed class FindMoviesAction {

    data class FindMoviesByTextAction(val queryText: String): FindMoviesAction()

}