package com.tzion.openmovies.factory


import com.tzion.testing.RandomFactory
import com.tzion.openmovies.data.remote.model.RemoteMovie
import com.tzion.openmovies.domain.model.DomainMovie
import com.tzion.openmovies.presentation.model.UiMovie

object MovieFactory {

    fun makeRemoteMovies(size: Int) = (0..size).map { makeRemoteMovie() }

    fun makeRemoteMovie() = RemoteMovie(
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString()
    )

    fun makeDomainMovie() = DomainMovie(
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString()
    )

    fun makeUiMovieList(count: Int): List<UiMovie> {
        val movies = mutableListOf<UiMovie>()
        repeat(count) {
            movies.add(makeUiMovie())
        }
        return movies
    }

    fun makeUiMovie() = UiMovie(
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString(),
        RandomFactory.generateString()
    )

}