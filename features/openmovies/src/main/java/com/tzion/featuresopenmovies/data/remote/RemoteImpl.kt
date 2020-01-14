package com.tzion.featuresopenmovies.data.remote

import com.tzion.featuresopenmovies.data.remote.model.RemoteSearch
import com.tzion.featuresopenmovies.data.source.Remote
import io.reactivex.Single
import javax.inject.Inject

class RemoteImpl @Inject constructor(
    private val retrofitWebService: RetrofitWebService): Remote {

    override fun findMoviesByText(text: String?): Single<RemoteSearch> = retrofitWebService
        .getMovies(text)

}