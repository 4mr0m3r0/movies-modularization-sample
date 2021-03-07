package com.tzion.openmovies.presentation.mapper

import com.tzion.openmovies.factory.MovieFactory.makeDomainMovie
import org.junit.Assert.assertEquals
import org.junit.Test

class UiMovieMapperTest {

    private val mapper = UiMovieMapper()

    @Test
    fun `given DomainMovie, when fromDomainToUi, then UiMovie`() {
        val domainMovie = makeDomainMovie()

        val uiMovie = with(mapper) { domainMovie.toUi() }

        assertEquals("movieId", domainMovie.movieId, uiMovie.movieId)
        assertEquals("title", domainMovie.title, uiMovie.title)
        assertEquals("year", domainMovie.year, uiMovie.year)
        assertEquals("poster", domainMovie.poster, uiMovie.poster)
        assertEquals("type", domainMovie.type, uiMovie.type)
    }

}