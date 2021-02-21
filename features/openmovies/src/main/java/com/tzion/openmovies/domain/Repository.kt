package com.tzion.openmovies.domain

import com.tzion.openmovies.domain.model.DomainMovie
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun findMoviesByText(text: String?): Flow<List<DomainMovie>>

}