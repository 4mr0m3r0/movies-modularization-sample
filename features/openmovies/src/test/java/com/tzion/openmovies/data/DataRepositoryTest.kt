package com.tzion.openmovies.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tzion.openmovies.factory.MovieFactory.makeDomainMovie
import com.tzion.openmovies.factory.SearchFactory.makeRemoteSearch
import com.tzion.openmovies.data.mapper.DataMovieMapper
import com.tzion.openmovies.data.remote.model.RemoteMovie
import com.tzion.openmovies.data.remote.model.RemoteSearch
import com.tzion.openmovies.data.source.DataSourceFactory
import com.tzion.openmovies.data.source.Remote
import com.tzion.openmovies.domain.model.DomainMovie
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class DataRepositoryTest {

    private val remote = mock<Remote>()
    private val factory = DataSourceFactory(remote)
    private val dataMovieMapper = mockk<DataMovieMapper>()
    private val dataRepository = DataRepository(factory, dataMovieMapper)

    @Test
    fun `given remote movies list, when findMoviesByText, then return domain movies list`() {
        val remoteSearch = makeRemoteSearch(5)
        stubRemoteFindMoviesByText(Single.just(remoteSearch))
        val domainMovies = mutableListOf<DomainMovie>()
        remoteSearch.search.forEach { remoteMovie ->
            val domainMovie = makeDomainMovie()
            domainMovies.add(domainMovie)
            stubDataMovieMapper(remoteMovie, domainMovie)
        }


        val testObserver = dataRepository.findMoviesByText(com.tzion.testing.RandomFactory.generateString()).test()

        testObserver.assertValue(domainMovies)
    }

    private fun stubRemoteFindMoviesByText(single: Single<RemoteSearch>) {
        whenever(remote.findMoviesByText(any())).thenReturn(single)
    }

    private fun stubDataMovieMapper(remoteMovie: RemoteMovie, domainMovie: DomainMovie) {
        with(dataMovieMapper) {
            io.mockk.every { remoteMovie.fromRemoteToDomain() } returns domainMovie
        }
    }

}