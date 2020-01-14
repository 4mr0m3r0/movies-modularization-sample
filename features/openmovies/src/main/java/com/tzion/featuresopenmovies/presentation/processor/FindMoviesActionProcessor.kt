package com.tzion.featuresopenmovies.presentation.processor

import com.tzion.corepresentation.execution.ExecutionThread
import com.tzion.featuresopenmovies.domain.FindMoviesByNameUseCase
import com.tzion.featuresopenmovies.presentation.action.FindMoviesAction
import com.tzion.featuresopenmovies.presentation.action.FindMoviesAction.*
import com.tzion.featuresopenmovies.presentation.result.FindMoviesResult
import com.tzion.featuresopenmovies.presentation.result.FindMoviesResult.*
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class FindMoviesActionProcessor @Inject constructor(
    private val findMoviesByTextUseCase: FindMoviesByNameUseCase,
    private val executionThread: ExecutionThread
) {

    var actionProcessor: ObservableTransformer<FindMoviesAction, FindMoviesResult>

    init {
        actionProcessor = ObservableTransformer { observableAction ->
            observableAction.publish { action ->
                action.ofType(FindMoviesByTextAction::class.java)
                    .compose(findMoviesByTextProcessor)
                    .cast(FindMoviesResult::class.java)
            }
        }
    }

    private val findMoviesByTextProcessor =
        ObservableTransformer<FindMoviesByTextAction, FindMoviesByTextResult> { actions ->
            actions.switchMap { action ->
                findMoviesByTextUseCase
                    .execute(action.queryText)
                    .toObservable()
                    .map { movies ->
                        FindMoviesByTextResult.Success(movies)
                    }
                    .cast(FindMoviesByTextResult::class.java)
                    .onErrorReturn(FindMoviesByTextResult::Error)
                    .startWith(FindMoviesByTextResult.InProcess)
                    .subscribeOn(executionThread.schedulerForSubscribing())
                    .observeOn(executionThread.schedulerForObserving())
            }
        }

}