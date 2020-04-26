package com.tzion.openmovies.data.remote

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tzion.testing.RandomFactory
import com.tzion.openmovies.factory.SearchFactory.makeRemoteSearch
import com.tzion.openmovies.data.remote.model.RemoteSearch
import io.reactivex.Single
import org.junit.Test

class RemoteImplTest {

    private val retrofitWebService = mock<RetrofitWebService>()
    private val remoteImpl = RemoteImpl(retrofitWebService)

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

        verify(retrofitWebService, times(1)).getMovies(text)
    }

    private fun stubGetMovies(single: Single<RemoteSearch>, text: String) {
        whenever(retrofitWebService.getMovies(text)).thenReturn(single)
    }

}