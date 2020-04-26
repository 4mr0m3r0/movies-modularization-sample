package com.tzion.openmovies.domain

import com.nhaarman.mockitokotlin2.*
import com.tzion.openmovies.factory.MovieFactory.makeDomainMovie
import com.tzion.openmovies.domain.model.DomainMovie
import io.reactivex.Single
import org.junit.Test

class FindMoviesByNameUseCaseTest {

    private val repository = mock<Repository>()
    private val useCase = FindMoviesByNameUseCase(repository)

    @Test
    fun `given params, when buildUseCaseObservable, then complete`() {
        val param = com.tzion.testing.RandomFactory.generateString()
        stubRepository(Single.just(listOf(makeDomainMovie())))

        val testObserver = useCase.execute(param).test()

        testObserver.assertComplete()
    }

    @Test
    fun `given params, when buildUseCaseObservable, then return data`() {
        val param = com.tzion.testing.RandomFactory.generateString()
        val domainMovie = makeDomainMovie()
        stubRepository(Single.just(listOf(domainMovie)))

        val testObserver = useCase.execute(param).test()

        testObserver.assertValue(listOf(domainMovie))
    }

    private fun stubRepository(single: Single<List<DomainMovie>>) {
        whenever(repository.findMoviesByText(any())).thenReturn(single)
    }

    @Test
    fun `given params, when buildUseCaseObservable, then call repository findMoviesByText`() {
        val param = com.tzion.testing.RandomFactory.generateString()

        useCase.execute(param)

        verify(repository, times(1)).findMoviesByText(param)
    }

}