package com.tzion.android.movies.data.remote

import com.tzion.android.movies.data.remote.model.RemoteSearch
import com.tzion.android.movies.data.repository.Remote
import io.reactivex.Single
import javax.inject.Inject

class RemoteImpl @Inject constructor(
    private val webServiceContract: WebServiceContract): Remote {

    override fun findMoviesByText(text: String?): Single<RemoteSearch> = webServiceContract
        .getMovies(text)

}