package com.tzion.android.movies.factory

import com.tzion.android.movies.data.remote.model.RemoteSearch
import com.tzion.android.movies.factory.MovieFactory.makeRemoteMovies

object SearchFactory {

    fun makeRemoteSearch(size: Int) = RemoteSearch(
        makeRemoteMovies(size)
    )

}