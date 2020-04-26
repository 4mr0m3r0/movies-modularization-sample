package com.tzion.openmovies.factory

import com.tzion.openmovies.factory.MovieFactory.makeRemoteMovies
import com.tzion.openmovies.data.remote.model.RemoteSearch

object SearchFactory {

    fun makeRemoteSearch(size: Int) = RemoteSearch(
        makeRemoteMovies(size)
    )

}