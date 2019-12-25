package com.tzion.android.movies.domain

import com.tzion.android.movies.domain.model.DomainMovie
import io.reactivex.Single
import javax.inject.Inject

class FindMoviesByTextUseCase @Inject constructor(private val repository: Repository) {

    fun execute(params: String?): Single<List<DomainMovie>> {
        require(!params.isNullOrEmpty())
        return repository.findMoviesByText(params)
    }

}