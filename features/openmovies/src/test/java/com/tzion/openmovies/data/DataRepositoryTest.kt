package com.tzion.openmovies.data

import com.tzion.openmovies.data.mapper.DataMovieMapper
import com.tzion.openmovies.data.remote.model.RemoteMovie
import com.tzion.openmovies.data.remote.model.RemoteSearch
import com.tzion.openmovies.data.source.Remote
import com.tzion.openmovies.domain.model.DomainMovie
import com.tzion.openmovies.factory.MovieFactory.makeDomainMovie
import com.tzion.openmovies.factory.SearchFactory.makeRemoteSearch
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class DataRepositoryTest {

    private val remote = mockk<Remote>()
    private val dataMovieMapper = mockk<DataMovieMapper>()
    private val dataRepository = DataRepository(remote, dataMovieMapper)

    @Test
    fun `given remote movies list, when findMoviesByText, then return domain movies list`() = runBlocking {
        val remoteSearch = makeRemoteSearch(5)
        stubRemoteFindMoviesByText(remoteSearch)
        val domainMovies = mutableListOf<DomainMovie>()
        remoteSearch.search.forEach { remoteMovie ->
            val domainMovie = makeDomainMovie()
            domainMovies.add(domainMovie)
            stubDataMovieMapper(remoteMovie, domainMovie)
        }


        val resultFlow = dataRepository.findMoviesByText(com.tzion.testing.RandomFactory.generateString())

        resultFlow.collect {  resultMovies ->
            assertEquals(domainMovies, resultMovies, "movies")
        }
    }

    private fun stubRemoteFindMoviesByText(remoteSearch: RemoteSearch) {
        coEvery { remote.findMoviesByText(any()) } returns remoteSearch
    }

    private fun stubDataMovieMapper(remoteMovie: RemoteMovie, domainMovie: DomainMovie) {
        with(dataMovieMapper) {
            every { remoteMovie.toDomain() } returns domainMovie
        }
    }

}