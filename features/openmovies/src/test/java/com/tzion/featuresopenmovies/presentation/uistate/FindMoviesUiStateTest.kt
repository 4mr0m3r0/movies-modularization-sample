package com.tzion.featuresopenmovies.presentation.uistate

import com.tzion.coretesting.RandomFactory
import com.tzion.featuresopenmovies.factory.MovieFactory.makeUiMovieList
import org.junit.Assert.*
import org.junit.Test

class FindMoviesUiStateTest {

    @Test
    fun `given Default, then assert values`() {
        val default = FindMoviesUiState.Default

        assertFalse(default.isLoading)
        assertTrue(default.withSearchInstructions)
        assertFalse(default.thereAreNotMoviesMatches)
        assertTrue(default.movies.isEmpty())
        assertFalse(default.withError)
        assertEquals(default.errorMessage, FindMoviesUiState.DEFAULT_ERROR_MSG)
    }

    @Test
    fun `given Loading, then assert values`() {
        val default = FindMoviesUiState.Loading

        assertTrue(default.isLoading)
        assertFalse(default.withSearchInstructions)
        assertFalse(default.thereAreNotMoviesMatches)
        assertTrue(default.movies.isEmpty())
        assertFalse(default.withError)
        assertEquals(default.errorMessage, FindMoviesUiState.DEFAULT_ERROR_MSG)
    }

    @Test
    fun `given EmptyList, then assert values`() {
        val default = FindMoviesUiState.EmptyList

        assertFalse(default.isLoading)
        assertFalse(default.withSearchInstructions)
        assertTrue(default.thereAreNotMoviesMatches)
        assertTrue(default.movies.isEmpty())
        assertFalse(default.withError)
        assertEquals(default.errorMessage, FindMoviesUiState.DEFAULT_ERROR_MSG)
    }

    @Test
    fun `given Success, then assert values`() {
        val default = FindMoviesUiState.Success(makeUiMovieList(5))

        assertFalse(default.isLoading)
        assertFalse(default.withSearchInstructions)
        assertFalse(default.thereAreNotMoviesMatches)
        assertTrue(default.movies.isNotEmpty())
        assertFalse(default.withError)
        assertEquals(default.errorMessage, FindMoviesUiState.DEFAULT_ERROR_MSG)
    }

    @Test
    fun `given Error, then assert values`() {
        val errorMsg = RandomFactory.generateString()
        val default = FindMoviesUiState.Error(errorMsg)

        assertFalse(default.isLoading)
        assertFalse(default.withSearchInstructions)
        assertFalse(default.thereAreNotMoviesMatches)
        assertTrue(default.movies.isEmpty())
        assertTrue(default.withError)
        assertEquals(default.errorMessage, errorMsg)
    }

}