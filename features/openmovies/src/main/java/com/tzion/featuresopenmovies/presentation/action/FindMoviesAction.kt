package com.tzion.featuresopenmovies.presentation.action

sealed class FindMoviesAction {

    data class FindMoviesByTextAction(val queryText: String): FindMoviesAction()

}