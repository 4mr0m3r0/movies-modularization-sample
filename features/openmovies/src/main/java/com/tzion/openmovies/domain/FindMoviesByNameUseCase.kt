package com.tzion.openmovies.domain

import com.tzion.openmovies.domain.model.DomainMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class FindMoviesByNameUseCase @Inject constructor(private val repository: Repository) {

    fun execute(params: String?): Flow<List<DomainMovie>> {
        require(!params.isNullOrEmpty())
        return repository.findMoviesByText(params)
    }

}