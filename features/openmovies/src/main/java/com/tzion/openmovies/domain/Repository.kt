package com.tzion.openmovies.domain

import com.tzion.openmovies.domain.model.DomainMovie
import io.reactivex.Single

interface Repository {

    fun findMoviesByText(text: String?): Single<List<DomainMovie>>

}