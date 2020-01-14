package com.tzion.featuresopenmovies.domain

import com.tzion.featuresopenmovies.domain.model.DomainMovie
import io.reactivex.Single

interface Repository {

    fun findMoviesByText(text: String?): Single<List<DomainMovie>>

}