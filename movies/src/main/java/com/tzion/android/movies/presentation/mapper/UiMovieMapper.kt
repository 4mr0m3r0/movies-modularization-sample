package com.tzion.android.movies.presentation.mapper

import com.tzion.android.movies.domain.model.DomainMovie
import com.tzion.android.movies.ui.model.UiMovie

class UiMovieMapper {

    fun DomainMovie.fromDomainToUi() = UiMovie(
        movieId = movieId ?: "",
        title = title ?: "",
        year = year ?: "",
        poster = poster ?: "",
        type = type ?: ""
    )

}