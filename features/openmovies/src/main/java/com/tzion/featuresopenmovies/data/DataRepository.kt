package com.tzion.featuresopenmovies.data

import com.tzion.featuresopenmovies.data.mapper.DataMovieMapper
import com.tzion.featuresopenmovies.data.source.DataSourceFactory
import com.tzion.featuresopenmovies.domain.Repository
import com.tzion.featuresopenmovies.domain.model.DomainMovie
import io.reactivex.Single
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val factory: DataSourceFactory,
    private val dataDataMovieMapper: DataMovieMapper
): Repository {

    override fun findMoviesByText(text: String?): Single<List<DomainMovie>> = factory
        .getRemote()
        .findMoviesByText(text)
        .map { remoteSearch ->
            with(dataDataMovieMapper) {
                remoteSearch.search.map { remoteMovie ->
                    remoteMovie.fromRemoteToDomain()
                }
            }
        }

}