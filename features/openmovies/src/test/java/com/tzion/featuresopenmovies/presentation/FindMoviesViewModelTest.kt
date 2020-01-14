package com.tzion.featuresopenmovies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.tzion.corepresentation.execution.ExecutionThreadEnvironment
import com.tzion.corepresentation.execution.ExecutionThreadFactory
import com.tzion.coretesting.RandomFactory
import com.tzion.featuresopenmovies.factory.MovieFactory.makeDomainMovie
import com.tzion.featuresopenmovies.domain.FindMoviesByNameUseCase
import com.tzion.featuresopenmovies.domain.Repository
import com.tzion.featuresopenmovies.domain.exception.NoMoviesResultsException
import com.tzion.featuresopenmovies.domain.model.DomainMovie
import com.tzion.featuresopenmovies.factory.MovieFactory.makeUiMovie
import com.tzion.featuresopenmovies.presentation.mapper.UiMovieMapper
import com.tzion.featuresopenmovies.presentation.model.UiMovie
import com.tzion.featuresopenmovies.presentation.processor.FindMoviesActionProcessor
import com.tzion.featuresopenmovies.presentation.uistate.FindMoviesUiState
import com.tzion.featuresopenmovies.presentation.userintent.FindMoviesUserIntent.*
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FindMoviesViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val repository = mock<Repository>()
    private val findMoviesByNameUseCase = FindMoviesByNameUseCase(repository)
    private val executionThread = ExecutionThreadFactory.makeExecutionThread(ExecutionThreadEnvironment.TESTING)
    private val processor = FindMoviesActionProcessor(findMoviesByNameUseCase, executionThread)
    private val uiMapper = mockk<UiMovieMapper>()
    private val viewModel = FindMoviesViewModel(processor, uiMapper)
    private lateinit var testObserver: TestObserver<FindMoviesUiState>

    companion object {
        const val POSITION_FOR_DEFAULT = 0
        const val POSITION_FOR_FIRST_STATE = 1
        const val POSITION_FOR_OTHER_STATES = 2
    }

    @Before
    fun setUp() {
        testObserver = viewModel.uiStates().test()
    }

    @After
    fun tearDown() {
        testObserver.dispose()
    }

    @Test
    fun `test default`() {
        testObserver.assertValueAt(POSITION_FOR_DEFAULT) { uiState ->
            uiState is FindMoviesUiState.Default
        }
    }

    @Test
    fun `given a list of movies, when process SearchFilterIntent, then state Loading`() {
        val domainMovie = makeDomainMovie()
        val uiMovie = makeUiMovie()
        stubPresentationMovieMapper(domainMovie, uiMovie)
        stubRepository(Single.just(listOf(domainMovie)))

        val testObserver = viewModel.uiStates().test()
        viewModel.processUserIntent(
            Observable.just(
                SearchFilterUserIntent(
                RandomFactory.generateString()
            )
        ))

        testObserver.assertValueAt(POSITION_FOR_FIRST_STATE) { movieUiState ->
            movieUiState is FindMoviesUiState.Loading
        }
    }

    @Test
    fun `given a list of movies, when process SearchFilterIntent, then state Success`() {
        val domainMovie = makeDomainMovie()
        val uiMovie = makeUiMovie()
        stubPresentationMovieMapper(domainMovie, uiMovie)
        stubRepository(Single.just(listOf(domainMovie)))

        val testObserver = viewModel.uiStates().test()
        viewModel.processUserIntent(Observable.just(
            SearchFilterUserIntent(RandomFactory.generateString())
        ))

        testObserver.assertValueAt(POSITION_FOR_OTHER_STATES) { movieUiState ->
            movieUiState is FindMoviesUiState.Success
        }
    }

    @Test
    fun `given an empty list of movies, when process SearchFilterIntent, then state EmptyList`() {
        stubRepository(Single.just(emptyList()))

        val testObserver = viewModel.uiStates().test()
        viewModel.processUserIntent(Observable.just(
            SearchFilterUserIntent(RandomFactory.generateString())
        ))

        testObserver.assertValueAt(POSITION_FOR_OTHER_STATES) { movieUiState ->
            movieUiState is FindMoviesUiState.EmptyList
        }
    }

    @Test
    fun `given NoMoviesResultsException, when process SearchFilterIntent, then state EmptyList`() {
        stubRepository(Single.error(NoMoviesResultsException(RandomFactory.generateString())))

        val testObserver = viewModel.uiStates().test()
        viewModel.processUserIntent(Observable.just(
            SearchFilterUserIntent(RandomFactory.generateString())
        ))

        testObserver.assertValueAt(POSITION_FOR_OTHER_STATES) { movieUiState ->
            movieUiState is FindMoviesUiState.EmptyList
        }
    }

    @Test
    fun `given AnyException, when process SearchFilterIntent, then state EmptyList`() {
        stubRepository(Single.error(Exception(RandomFactory.generateString())))

        val testObserver = viewModel.uiStates().test()
        viewModel.processUserIntent(Observable.just(SearchFilterUserIntent(RandomFactory.generateString())))

        testObserver.assertValueAt(POSITION_FOR_OTHER_STATES) { movieUiState ->
            movieUiState is FindMoviesUiState.Error
        }
    }

    private fun stubPresentationMovieMapper(domainMovie: DomainMovie,
                                            uiMovie: UiMovie) {
        with(uiMapper) {
            every { domainMovie.fromDomainToUi() } returns uiMovie
        }
    }

    private fun stubRepository(single: Single<List<DomainMovie>>) {
        whenever(repository.findMoviesByText(any())).thenReturn(single)
    }

}