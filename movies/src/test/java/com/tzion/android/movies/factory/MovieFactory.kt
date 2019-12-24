package com.tzion.android.movies.factory

import com.tzion.android.core_testing.RandomFactory
import com.tzion.android.movies.data.remote.model.RemoteMovie
import com.tzion.android.movies.domain.model.DomainMovie

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

}