package com.tzion.android.movies.data

import com.tzion.android.movies.data.mapper.DataMovieMapper
import com.tzion.android.movies.data.source.DataSourceFactory
import com.tzion.android.movies.domain.Repository
import com.tzion.android.movies.domain.model.DomainMovie
import io.reactivex.Single
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val factory: DataSourceFactory,
    private val dataMovieMapper: DataMovieMapper): Repository {

    override fun findMoviesByText(text: String?): Single<List<DomainMovie>> = factory
        .getRemote()
        .findMoviesByText(text)
        .map { remoteSearch ->
            with(dataMovieMapper) {
                remoteSearch.search.map { remoteMovie ->
                    remoteMovie.fromRemoteToDomain()
                }
            }
        }

}