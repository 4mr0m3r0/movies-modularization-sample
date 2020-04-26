package com.tzion.openmovies.data.remote

import com.tzion.openmovies.data.remote.model.RemoteSearch
import com.tzion.openmovies.data.source.Remote
import io.reactivex.Single
import javax.inject.Inject

class RemoteImpl @Inject constructor(
    private val retrofitWebService: RetrofitWebService): Remote {

    override fun findMoviesByText(text: String?): Single<RemoteSearch> = retrofitWebService
        .getMovies(text)

}