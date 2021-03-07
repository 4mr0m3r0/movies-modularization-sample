package com.tzion.openmovies.presentation.mapper

import com.tzion.openmovies.domain.model.DomainMovie
import com.tzion.openmovies.presentation.model.UiMovie
import com.tzion.util.DefaultValues
import javax.inject.Inject

class UiMovieMapper @Inject constructor() {

    fun DomainMovie.toUi() = UiMovie(
        movieId = movieId ?: DefaultValues.emptyString(),
        title = title ?: DefaultValues.emptyString(),
        year = year ?: DefaultValues.emptyString(),
        poster = poster ?: DefaultValues.emptyString(),
        type = type ?: DefaultValues.emptyString()
    )

}