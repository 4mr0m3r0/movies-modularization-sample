package com.tzion.openmovies.data.remote

import com.tzion.openmovies.data.remote.model.RemoteSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitWebService {

    @GET("/")
    fun getMovies(@Query("s") searchCriteria: String?, @Query("apikey") apiKey: String = "e818df3e"): Single<RemoteSearch>

}