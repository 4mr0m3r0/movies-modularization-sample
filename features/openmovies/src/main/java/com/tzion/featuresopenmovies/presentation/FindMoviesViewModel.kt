package com.tzion.featuresopenmovies.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tzion.corepresentation.MviViewModel
import com.tzion.featuresopenmovies.domain.exception.NoMoviesResultsException
import com.tzion.featuresopenmovies.domain.model.DomainMovie
import com.tzion.featuresopenmovies.presentation.action.FindMoviesAction
import com.tzion.featuresopenmovies.presentation.action.FindMoviesAction.*
import com.tzion.featuresopenmovies.presentation.mapper.UiMovieMapper
import com.tzion.featuresopenmovies.presentation.processor.FindMoviesActionProcessor
import com.tzion.featuresopenmovies.presentation.result.FindMoviesResult
import com.tzion.featuresopenmovies.presentation.result.FindMoviesResult.*
import com.tzion.featuresopenmovies.presentation.uistate.FindMoviesUiState
import com.tzion.featuresopenmovies.presentation.userintent.FindMoviesUserIntent
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
            is FindMoviesUserIntent.SearchFilterUserIntent -> FindMoviesByTextAction(userIntent.queryText)
        }

    private val reducer: BiFunction<FindMoviesUiState, FindMoviesResult, FindMoviesUiState>
        get() = BiFunction { previousState: FindMoviesUiState, result: FindMoviesResult ->
            when (result) {
                is FindMoviesByTextResult -> when (result) {
                    is FindMoviesByTextResult.Success -> setSuccessUiState(result.movies)
                    is FindMoviesByTextResult.Error -> setErrorUiState(result.error)
                    FindMoviesByTextResult.InProcess -> FindMoviesUiState.Loading
                }
            }
        }

    private fun setErrorUiState(error: Throwable): FindMoviesUiState {
        return if (error is NoMoviesResultsException) {
            FindMoviesUiState.EmptyList
        } else {
            FindMoviesUiState.Error(error.localizedMessage)
        }
    }

    private fun setSuccessUiState(domainMovies: List<DomainMovie>): FindMoviesUiState {
        return if (domainMovies.isEmpty()) {
            FindMoviesUiState.EmptyList
        } else {
            FindMoviesUiState.Success(domainMovies.map { movie ->
                with(uiMovieMapper) { movie.fromDomainToUi() }
            })
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