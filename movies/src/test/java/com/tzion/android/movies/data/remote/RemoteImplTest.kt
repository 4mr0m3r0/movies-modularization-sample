package com.tzion.android.movies.data.remote

import com.nhaarman.mockitokotlin2.*
import com.tzion.android.core_testing.RandomFactory
import com.tzion.android.movies.data.remote.model.RemoteSearch
import com.tzion.android.movies.factory.MovieFactory.makeRemoteMovies
import com.tzion.android.movies.factory.SearchFactory.makeRemoteSearch
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoteImplTest {

    private val webServiceContract = mock<WebServiceContract>()
    private val remoteImpl = RemoteImpl(webServiceContract)

    @Test
    fun `given text as param, when findMoviesByText, then complete`() {
        val text = RandomFactory.generateString()
        stubGetMovies(Single.just(makeRemoteSearch(5)), text)

        val testObserver = remoteImpl.findMoviesByText(text).test()

        testObserver.assertComplete()
    }

    @Test
    fun `given text as param, when findMoviesByText, then return data`() {
        val text = RandomFactory.generateString()
        val remoteSearch = makeRemoteSearch(5)
        stubGetMovies(Single.just(remoteSearch), text)

        val testObserver = remoteImpl.findMoviesByText(text).test()

        testObserver.assertValue(remoteSearch)
    }

    @Test
    fun `given text as param, when findMoviesByText, then call to getMovies`() {
        val text = RandomFactory.generateString()
        stubGetMovies(Single.just(makeRemoteSearch(5)), text)

        remoteImpl.findMoviesByText(text).test()

        verify(webServiceContract, times(1)).getMovies(text)
    }

    private fun stubGetMovies(single: Single<RemoteSearch>, text: String) {
        whenever(webServiceContract.getMovies(text)).thenReturn(single)
    }

}