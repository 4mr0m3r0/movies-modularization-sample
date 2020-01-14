package com.tzion.featuresopenmovies.data.mapper

import com.tzion.featuresopenmovies.factory.MovieFactory.makeRemoteMovie
import org.junit.Assert.*
import org.junit.Test

class DataMovieMapperTest {

    private val dataMovieMapper = DataMovieMapper()

    @Test
    fun `given remote movie, when fromRemoteToDomain, then return DomainMovie`() {
        val remoteMovie = makeRemoteMovie()

        val domainMovie = with(dataMovieMapper) { remoteMovie.fromRemoteToDomain() }

        assertEquals("movieId", domainMovie.movieId, remoteMovie.imdbId)
        assertEquals("title", domainMovie.title, remoteMovie.title)
        assertEquals("year", domainMovie.year, remoteMovie.year)
        assertEquals("poster", domainMovie.poster, remoteMovie.poster)
        assertEquals("type", domainMovie.type, remoteMovie.type)
    }

}