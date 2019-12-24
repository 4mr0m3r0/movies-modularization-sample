package com.tzion.android.movies.presentation.procesor

import com.tzion.android.movies.domain.FindMoviesByTextUseCase
import com.tzion.android.movies.presentation.action.ListMoviesAction
import com.tzion.android.movies.presentation.action.ListMoviesAction.*
import com.tzion.android.movies.presentation.result.ListMoviesResult
import com.tzion.android.movies.presentation.result.ListMoviesResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class ListMoviesActionProcessor @Inject constructor(
    private val findMoviesByTextUseCase: FindMoviesByTextUseCase) {

    private val showListEmptyProcessor =
        ObservableTransformer<ShowListEmptyAction, ShowListEmptyResult> { actions ->
            actions.flatMap {
                Observable.just(ShowListEmptyResult.Success)
            }
        }

    private val findMoviesByTextProcessor =
        ObservableTransformer<FindMoviesByTextAction, FindMoviesByTextResult> { actions ->
            actions.switchMap { action ->
                findMoviesByTextUseCase
                    .execute(action.queryText)
                    .toObservable()
                    .map { domainMovies ->
                        FindMoviesByTextResult.Success(domainMovies)
                    }
                    .cast(FindMoviesByTextResult::class.java)
                    .onErrorReturn(FindMoviesByTextResult::Error)
                    .startWith(FindMoviesByTextResult.InFlight)
            }
        }

    var actionProcessor = ObservableTransformer<ListMoviesAction, ListMoviesResult> { actions ->
        actions.publish { shared ->
            Observable.merge(
                shared.ofType(ShowListEmptyAction::class.java).compose(showListEmptyProcessor),
                shared.ofType(FindMoviesByTextAction::class.java).compose(findMoviesByTextProcessor)
            ).mergeWith(
                shared.filter { action ->
                    action !is ShowListEmptyAction
                            && action !is FindMoviesByTextAction
                }.flatMap {
                    Observable.error<ListMoviesResult>(IllegalArgumentException("Unknown Action type: $it"))
                }
            )
        }
    }

}