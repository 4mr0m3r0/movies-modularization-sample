package com.tzion.android.movies.factory

import com.tzion.coretesting.RandomFactory
import com.tzion.android.movies.data.remote.model.RemoteMovie
import com.tzion.android.movies.domain.model.DomainMovie

object MovieFactory {

    fun makeRemoteMovies(size: Int) = (0..size).map { makeRemoteMovie() }

    fun makeRemoteMovie() = RemoteMovie(
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString()
    )

    fun makeDomainMovie() = DomainMovie(
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString(),
        com.tzion.coretesting.RandomFactory.generateString()
    )

}