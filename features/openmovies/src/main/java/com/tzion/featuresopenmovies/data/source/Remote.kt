package com.tzion.featuresopenmovies.data.source

import com.tzion.featuresopenmovies.data.remote.model.RemoteSearch
import io.reactivex.Single

interface Remote {

    fun findMoviesByText(text: String?): Single<RemoteSearch>

}