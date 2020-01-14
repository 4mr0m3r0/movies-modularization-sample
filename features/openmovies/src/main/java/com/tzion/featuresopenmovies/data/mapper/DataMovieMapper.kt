package com.tzion.featuresopenmovies.data.mapper

import com.tzion.featuresopenmovies.data.remote.model.RemoteMovie
import com.tzion.featuresopenmovies.domain.model.DomainMovie
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