package com.tzion.openmovies.domain

import com.tzion.openmovies.domain.model.DomainMovie
import com.tzion.openmovies.factory.MovieFactory.makeDomainMovie
import com.tzion.testing.RandomFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class FindMoviesByNameUseCaseTest {

    private val repository = mockk<Repository>()
    private val useCase = FindMoviesByNameUseCase(repository)

    @Test
    fun `given params, when execute, then return data`() = runBlocking {
        val param = RandomFactory.generateString()
        val domainMovie = makeDomainMovie()
        stubRepository(listOf(domainMovie))

        val resultFlow = useCase.execute(param)

        resultFlow.collect { resultMovies ->
            assertEquals(listOf(domainMovie), resultMovies, "movies")
        }
    }

    @Test
    fun `given params, when execute, then call repository findMoviesByText`() = runBlocking {
        val param = RandomFactory.generateString()
        val domainMovie = makeDomainMovie()
        stubRepository(listOf(domainMovie))

        useCase.execute(param)

        coVerify(atMost = 1) { repository.findMoviesByText(param) }
    }

    private fun stubRepository(domainMovies: List<DomainMovie>) {
        coEvery { repository.findMoviesByText(any()) } returns flow { emit(domainMovies) }
    }

}