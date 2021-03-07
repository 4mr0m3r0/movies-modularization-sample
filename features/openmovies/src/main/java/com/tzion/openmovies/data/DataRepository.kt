package com.tzion.openmovies.data

import com.tzion.openmovies.data.mapper.DataMovieMapper
import com.tzion.openmovies.data.source.Remote
import com.tzion.openmovies.domain.Repository
import com.tzion.openmovies.domain.model.DomainMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remote: Remote,
    private val dataDataMovieMapper: DataMovieMapper
): Repository {

    override fun findMoviesByText(text: String?): Flow<List<DomainMovie>> = flow {
        val remoteSearch = remote.findMoviesByText(text)
        val domainMovies = with(dataDataMovieMapper) {
            remoteSearch.search.map { remoteMovie ->
                remoteMovie.toDomain()
            }
        }
        emit(domainMovies)
    }

}