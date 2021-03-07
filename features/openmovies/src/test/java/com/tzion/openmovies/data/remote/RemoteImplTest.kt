package com.tzion.openmovies.data.remote

import com.tzion.openmovies.data.remote.model.RemoteSearch
import com.tzion.openmovies.data.remote.retrofit.WebServiceRetrofit
import com.tzion.openmovies.factory.SearchFactory.makeRemoteSearch
import com.tzion.testing.RandomFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class RemoteImplTest {

    private val retrofitWebService = mockk<WebServiceRetrofit>()
    private val remoteImpl = RemoteImpl(retrofitWebService)

    @Test
    fun `given text as param, when findMoviesByText, then return data`() = runBlocking {
        val text = RandomFactory.generateString()
        val remoteSearch = makeRemoteSearch(5)
        stubGetMovies(remoteSearch, text)

        val remoteSearchResult = remoteImpl.findMoviesByText(text)

        assertEquals(remoteSearch, remoteSearchResult, "remote Search")
    }

    @Test
    fun `given text as param, when findMoviesByText, then call to getMovies`() = runBlocking {
        val text = RandomFactory.generateString()
        stubGetMovies(makeRemoteSearch(5), text)

        remoteImpl.findMoviesByText(text)

        coVerify (atMost = 1) { retrofitWebService.getMovies(text) }
    }

    private fun stubGetMovies(remoteSearch: RemoteSearch, text: String) {
        coEvery { retrofitWebService.getMovies(text) } returns remoteSearch
    }

}