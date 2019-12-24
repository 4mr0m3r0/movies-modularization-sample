package com.tzion.android.movies.data.repository

import com.tzion.android.movies.data.remote.model.RemoteSearch
import io.reactivex.Single

interface Remote {

    fun findMoviesByText(text: String?): Single<RemoteSearch>

}