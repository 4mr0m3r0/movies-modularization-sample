package com.tzion.android.movies.domain

import com.tzion.android.movies.domain.model.DomainMovie
import io.reactivex.Single

interface Repository {

    fun findMoviesByText(text: String?): Single<List<DomainMovie>>

}