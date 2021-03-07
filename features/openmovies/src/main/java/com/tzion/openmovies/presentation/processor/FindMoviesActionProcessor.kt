package com.tzion.openmovies.presentation.processor

import com.tzion.mvi.execution.ExecutionThread
import com.tzion.openmovies.domain.FindMoviesByNameUseCase
import com.tzion.openmovies.presentation.action.FindMoviesAction
import com.tzion.openmovies.presentation.action.FindMoviesAction.FindMoviesByTextAction
import com.tzion.openmovies.presentation.mapper.UiMovieMapper
import com.tzion.openmovies.presentation.result.FindMoviesResult
import com.tzion.openmovies.presentation.result.FindMoviesResult.FindMoviesByTextResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FindMoviesActionProcessor @Inject constructor(
    private val findMoviesByTextUseCase: FindMoviesByNameUseCase,
    private val uiMovieMapper: UiMovieMapper,
    private val executionThread: ExecutionThread
) {

    fun actionProcessor(findMoviesAction: FindMoviesAction): Flow<FindMoviesResult> =
        when (findMoviesAction) {
            is FindMoviesByTextAction -> findMoviesByTextProcessor(findMoviesAction)
        }

    private fun findMoviesByTextProcessor(
        action: FindMoviesByTextAction
    ): Flow<FindMoviesByTextResult> =
        findMoviesByTextUseCase
            .execute(action.queryText)
            .map { movies ->
                with(uiMovieMapper) { movies.map { it.toUi() } }
            }
            .map { uiMovies ->
                FindMoviesByTextResult.Success(uiMovies) as FindMoviesByTextResult
            }
            .onStart { emit(FindMoviesByTextResult.InProcess) }
            .catch { cause: Throwable -> emit(FindMoviesByTextResult.Error(cause)) }
            .flowOn(executionThread.ioThread())

}