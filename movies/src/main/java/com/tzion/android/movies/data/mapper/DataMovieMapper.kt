package com.tzion.android.movies.data.mapper

import com.tzion.android.movies.data.remote.model.RemoteMovie
import com.tzion.android.movies.data.remote.model.RemoteSearch
import com.tzion.android.movies.domain.model.DomainMovie
import javax.inject.Inject

class DataMovieMapper @Inject constructor() {

    fun RemoteMovie.fromRemoteToDomain() = DomainMovie(
        movieId = imdbId,
        title = title,
        year = year,
        poster = poster,
        type = type
    )

}