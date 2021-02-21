package com.tzion.openmovies.presentation.action

import com.tzion.mvi.events.MviAction

sealed class FindMoviesAction : MviAction {

    data class FindMoviesByTextAction(val queryText: String): FindMoviesAction()

}