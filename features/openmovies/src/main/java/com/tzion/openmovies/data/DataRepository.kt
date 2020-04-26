package com.tzion.openmovies.data

import com.tzion.openmovies.data.mapper.DataMovieMapper
import com.tzion.openmovies.data.source.DataSourceFactory
import com.tzion.openmovies.domain.Repository
import com.tzion.openmovies.domain.model.DomainMovie
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