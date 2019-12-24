package com.tzion.android.movies.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tzion.android.core_testing.RandomFactory
import com.tzion.android.movies.data.mapper.DataMovieMapper
import com.tzion.android.movies.data.remote.model.RemoteMovie
import com.tzion.android.movies.data.remote.model.RemoteSearch
import com.tzion.android.movies.data.repository.Remote
import com.tzion.android.movies.data.source.DataSourceFactory
import com.tzion.android.movies.domain.model.DomainMovie
import com.tzion.android.movies.factory.MovieFactory.makeDomainMovie
import com.tzion.android.movies.factory.SearchFactory.makeRemoteSearch
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
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


        val testObserver = dataRepository.findMoviesByText(RandomFactory.generateString()).test()

        testObserver.assertValue(domainMovies)
    }

    private fun stubRemoteFindMoviesByText(single: Single<RemoteSearch>) {
        whenever(remote.findMoviesByText(any())).thenReturn(single)
    }

    private fun stubDataMovieMapper(remoteMovie: RemoteMovie, domainMovie: DomainMovie) {
        with(dataMovieMapper) {
            every { remoteMovie.fromRemoteToDomain() } returns domainMovie
        }
    }

}