package com.tzion.openmovies.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tzion.mvi.MviPresentation
import com.tzion.openmovies.domain.exception.NoMoviesResultsException
import com.tzion.openmovies.domain.model.DomainMovie
import com.tzion.openmovies.presentation.action.FindMoviesAction
import com.tzion.openmovies.presentation.action.FindMoviesAction.*
import com.tzion.openmovies.presentation.mapper.UiMovieMapper
import com.tzion.openmovies.presentation.processor.FindMoviesActionProcessor
import com.tzion.openmovies.presentation.result.FindMoviesResult
import com.tzion.openmovies.presentation.result.FindMoviesResult.*
import com.tzion.openmovies.presentation.uistate.FindMoviesUiState
import com.tzion.openmovies.presentation.userintent.FindMoviesUserIntent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class FindMoviesViewModel @Inject constructor(
    private val findMoviesActionProcessor: FindMoviesActionProcessor,
    private val uiMovieMapper: UiMovieMapper)
    : ViewModel(), MviPresentation<FindMoviesUserIntent, FindMoviesUiState> {

    private val userIntentsSubject: PublishSubject<FindMoviesUserIntent> = PublishSubject.create()
    private val disposable = CompositeDisposable()
    private val liveDataUiStates = MutableLiveData<FindMoviesUiState>()
    private val uiStatesObservable: Observable<FindMoviesUiState> = compose()

    private fun compose(): Observable<FindMoviesUiState> {
        return userIntentsSubject
            .map { userIntent -> transformUserIntentIntoActions(userIntent) }
            .compose(findMoviesActionProcessor.actionProcessor)
            .scan(FindMoviesUiState.Default, reducer)
    }

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
        disposable += uiStatesObservable.subscribe(liveDataUiStates::setValue) { }
    }

    override fun processUserIntents(userIntents: Observable<FindMoviesUserIntent>) {
        userIntents.subscribe(userIntentsSubject)
    }

    override fun uiStates(): Observable<FindMoviesUiState> = uiStatesObservable

    fun liveData(): LiveData<FindMoviesUiState> = liveDataUiStates

}