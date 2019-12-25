package com.tzion.android.movies.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tzion.corepresentation.MviViewModel
import com.tzion.android.movies.presentation.action.FindMoviesAction
import com.tzion.android.movies.presentation.action.FindMoviesAction.*
import com.tzion.android.movies.presentation.userintent.FindMoviesUserIntent
import com.tzion.android.movies.presentation.userintent.FindMoviesUserIntent.*
import com.tzion.android.movies.presentation.mapper.UiMovieMapper
import com.tzion.android.movies.presentation.procesor.FindMoviesActionProcessor
import com.tzion.android.movies.presentation.result.FindMoviesResult
import com.tzion.android.movies.presentation.result.FindMoviesResult.*
import com.tzion.android.movies.presentation.uistate.FindMoviesUiState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class FindMoviesViewModel @Inject constructor(
    private val findMoviesActionProcessor: FindMoviesActionProcessor,
    private val uiMovieMapper: UiMovieMapper
) : ViewModel(), MviViewModel<FindMoviesUserIntent, FindMoviesUiState> {

    private val userIntentsSubject: PublishSubject<FindMoviesUserIntent> = PublishSubject.create()
    private val disposable = CompositeDisposable()
    private val liveDataUiStates = MutableLiveData<FindMoviesUiState>()
    private val uiStatesObservable: Observable<FindMoviesUiState> = compose()

    private fun compose(): Observable<FindMoviesUiState> {
        return userIntentsSubject
//            .compose(intentFilter)
            .map { userIntent -> transformUserIntentIntoActions(userIntent) }
            .compose(findMoviesActionProcessor.actionProcessor)
            .scan(FindMoviesUiState.Default, reducer)
            .distinctUntilChanged()
    }

//    private val intentFilter: ObservableTransformer<FindMoviesUserIntent, FindMoviesUserIntent> =
//        ObservableTransformer { intents ->
//            intents.publish { shared ->
//                Observable.merge(
//                    shared.ofType(InitialIntent::class.java).take(1),
//                    shared.filter { intent -> intent !is InitialIntent }
//                )
//            }
//        }

    private fun transformUserIntentIntoActions(userIntent: FindMoviesUserIntent): FindMoviesAction =
        when (userIntent) {
            is SearchFilterIntent -> FindMoviesByTextAction(userIntent.queryText)
        }

    private val reducer = BiFunction { previousState: FindMoviesUiState, result: FindMoviesResult ->
        when (result) {
            is FindMoviesByTextResult -> when (result) {
                is FindMoviesByTextResult.Success -> FindMoviesUiState.Success(with(uiMovieMapper) {
                    result.movies.map { it.fromDomainToUi() }
                })
                is FindMoviesByTextResult.Error -> FindMoviesUiState.Error(result.error.localizedMessage)
                FindMoviesByTextResult.InProcess -> FindMoviesUiState.Loading
            }
        }
    }

    init {
        disposable += uiStatesObservable.subscribe(liveDataUiStates::setValue) {
            Log.d(this::class.java.simpleName, "subscriber error")
        }
    }

    override fun processUserIntent(userIntents: Observable<FindMoviesUserIntent>) {
        userIntents.subscribe(userIntentsSubject)
    }

    override fun uiStates(): Observable<FindMoviesUiState> = uiStatesObservable

    fun liveData(): LiveData<FindMoviesUiState> = liveDataUiStates

}