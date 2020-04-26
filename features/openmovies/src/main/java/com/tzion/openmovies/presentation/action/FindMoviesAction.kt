package com.tzion.openmovies.presentation.action

sealed class FindMoviesAction {

    data class FindMoviesByTextAction(val queryText: String): FindMoviesAction()

}