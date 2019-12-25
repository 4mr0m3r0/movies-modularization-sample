package com.tzion.android.movies.domain

import com.nhaarman.mockitokotlin2.*
import com.tzion.android.core_domain.executor.PostExecutionThread
import com.tzion.android.core_domain.executor.ThreadExecutor
import com.tzion.coretesting.RandomFactory
import com.tzion.android.movies.domain.model.DomainMovie
import com.tzion.android.movies.factory.MovieFactory.makeDomainMovie
import io.reactivex.Single
import org.junit.Test

class FindMoviesByTextUseCaseTest {

    private val repository = mock<Repository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val useCase = FindMoviesByTextUseCase(repository, threadExecutor, postExecutionThread)

    @Test
    fun `given params, when buildUseCaseObservable, then complete`() {
        val param = com.tzion.coretesting.RandomFactory.generateString()
        stubRepository(Single.just(listOf(makeDomainMovie())))

        val testObserver = useCase.buildUseCaseObservable(param).test()

        testObserver.assertComplete()
    }

    @Test
    fun `given params, when buildUseCaseObservable, then return data`() {
        val param = com.tzion.coretesting.RandomFactory.generateString()
        val domainMovie = makeDomainMovie()
        stubRepository(Single.just(listOf(domainMovie)))

        val testObserver = useCase.buildUseCaseObservable(param).test()

        testObserver.assertValue(listOf(domainMovie))
    }

    private fun stubRepository(single: Single<List<DomainMovie>>) {
        whenever(repository.findMoviesByText(any())).thenReturn(single)
    }

    @Test
    fun `given params, when buildUseCaseObservable, then call repository findMoviesByText`() {
        val param = com.tzion.coretesting.RandomFactory.generateString()

        useCase.buildUseCaseObservable(param)

        verify(repository, times(1)).findMoviesByText(param)
    }

}