package com.tzion.featuresopenmovies.presentation.mapper

import com.tzion.featuresopenmovies.domain.model.DomainMovie
import com.tzion.featuresopenmovies.presentation.model.UiMovie

class UiMovieMapper {

    fun DomainMovie.fromDomainToUi() = UiMovie(
        movieId = movieId ?: "",
        title = title ?: "",
        year = year ?: "",
        poster = poster ?: "",
        type = type ?: ""
    )

}