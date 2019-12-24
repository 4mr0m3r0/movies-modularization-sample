package com.tzion.android.movies.presentation.action

sealed class ListMoviesAction {

    object ShowListEmptyAction: ListMoviesAction()
    data class FindMoviesByTextAction(val queryText: String): ListMoviesAction()

}