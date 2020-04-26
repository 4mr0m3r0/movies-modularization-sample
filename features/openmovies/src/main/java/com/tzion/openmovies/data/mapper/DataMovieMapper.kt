package com.tzion.openmovies.data.mapper

import com.tzion.openmovies.data.remote.model.RemoteMovie
import com.tzion.openmovies.domain.model.DomainMovie
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