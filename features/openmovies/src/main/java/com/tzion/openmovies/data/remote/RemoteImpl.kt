package com.tzion.openmovies.data.remote

import com.tzion.openmovies.data.remote.model.RemoteSearch
import com.tzion.openmovies.data.remote.retrofit.WebServiceRetrofit
import com.tzion.openmovies.data.source.Remote
import io.reactivex.Single
import javax.inject.Inject

class RemoteImpl @Inject constructor(
    private val webServiceRetrofit: WebServiceRetrofit
): Remote {

    override fun findMoviesByText(text: String?): Single<RemoteSearch> = webServiceRetrofit
        .getMovies(text)

}