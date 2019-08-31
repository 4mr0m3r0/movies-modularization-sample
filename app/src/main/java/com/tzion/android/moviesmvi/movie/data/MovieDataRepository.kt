package com.tzion.android.moviesmvi.movie.data

import com.tzion.android.moviesmvi.movie.domain.MovieRepository
import com.tzion.android.moviesmvi.movie.domain.model.DomainMovie
import io.reactivex.Single
import javax.inject.Inject

class MovieDataRepository @Inject constructor() : MovieRepository {

    override fun findMoviesByText(text: String?): Single<List<DomainMovie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}