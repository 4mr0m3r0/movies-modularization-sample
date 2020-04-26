package com.tzion.openmovies.data.source

import com.tzion.openmovies.data.remote.model.RemoteSearch
import io.reactivex.Single

interface Remote {

    fun findMoviesByText(text: String?): Single<RemoteSearch>

}