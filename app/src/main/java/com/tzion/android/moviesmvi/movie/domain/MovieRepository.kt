package com.tzion.android.moviesmvi.movie.domain

import com.tzion.android.moviesmvi.movie.domain.model.DomainMovie
import io.reactivex.Single

interface MovieRepository {

    fun findMoviesByText(text: String?): Single<List<DomainMovie>>

}