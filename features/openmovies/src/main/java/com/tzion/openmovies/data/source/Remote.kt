package com.tzion.openmovies.data.source

import com.tzion.openmovies.data.remote.model.RemoteSearch

interface Remote {

    suspend fun findMoviesByText(text: String?): RemoteSearch

}