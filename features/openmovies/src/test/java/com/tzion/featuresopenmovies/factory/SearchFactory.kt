package com.tzion.featuresopenmovies.factory

import com.tzion.featuresopenmovies.factory.MovieFactory.makeRemoteMovies
import com.tzion.featuresopenmovies.data.remote.model.RemoteSearch

object SearchFactory {

    fun makeRemoteSearch(size: Int) = RemoteSearch(
        makeRemoteMovies(size)
    )

}