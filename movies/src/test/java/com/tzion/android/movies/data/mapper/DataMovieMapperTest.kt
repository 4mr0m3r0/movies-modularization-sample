package com.tzion.android.movies.data.mapper

import com.tzion.android.movies.factory.MovieFactory.makeRemoteMovie
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
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