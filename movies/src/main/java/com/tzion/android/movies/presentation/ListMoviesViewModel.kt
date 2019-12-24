package com.tzion.android.movies.presentation

import androidx.lifecycle.ViewModel
import com.tzion.android.core_presentation.MviViewModel
import com.tzion.android.movies.presentation.action.ListMoviesAction
import com.tzion.android.movies.presentation.action.ListMoviesAction.*
import com.tzion.android.movies.presentation.intent.ListMoviesIntent
import com.tzion.android.movies.presentation.intent.ListMoviesIntent.*
import com.tzion.android.movies.presentation.mapper.UiMovieMapper
import com.tzion.android.movies.presentation.procesor.ListMoviesActionProcessor
import com.tzion.android.movies.presentation.result.ListMoviesResult
import com.tzion.android.movies.presentation.result.ListMoviesResult.*
import com.tzion.android.movies.presentation.state.ListMoviesUiState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ListMoviesViewModel @Inject constructor(
    private val listMoviesActionProcessor: ListMoviesActionProcessor,
    private val uiMovieMapper: UiMovieMapper): ViewModel(), MviViewModel<ListMoviesIntent, ListMoviesUiState> {

    private val intentsSubject: PublishSubject<ListMoviesIntent> = PublishSubject.create()
//    private val statesSubject: PublishSubject<ListMoviesUiState> = PublishSubject.create() //TODO: find the reason of this https://www.youtube.com/watch?v=PXBXcHQeDLE&t=1682s
    private val statesObservable: Observable<ListMoviesUiState> = compose()
//    private val disposables = CompositeDisposable()

    private fun compose(): Observable<ListMoviesUiState> {
        return intentsSubject
            .compose(intentFilter)
            .map { intent -> actionFromIntent(intent) }
            .compose(listMoviesActionProcessor.actionProcessor)
            .scan(ListMoviesUiState.default(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private val reducer = BiFunction { previousState: ListMoviesUiState, result: ListMoviesResult ->
        when (result) {
            is FindMoviesByTextResult -> when (result) {
                is FindMoviesByTextResult.Success -> {
                    previousState.copy(
                        isLoading = false,
                        movies = with(uiMovieMapper) { result.movies.map { it.fromDomainToUi() } }
                    )
                }
                is FindMoviesByTextResult.Error -> previousState.copy(isLoading = false, error = result.error)
                FindMoviesByTextResult.InFlight -> previousState.copy(isLoading = true)
            }
            ShowListEmptyResult.Success -> previousState
        }
    }

    override fun processIntent(intents: Observable<ListMoviesIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<ListMoviesUiState> = statesObservable

    private val intentFilter: ObservableTransformer<ListMoviesIntent, ListMoviesIntent> =
        ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(InitialIntent::class.java).take(1),
                    shared.filter { intent -> intent !is InitialIntent }
                )
            }
        }

    private fun actionFromIntent(intent: ListMoviesIntent): ListMoviesAction =
        when(intent) {
            InitialIntent           -> ShowListEmptyAction
            is SearchFilterIntent   -> FindMoviesByTextAction(intent.queryText)
        }


}