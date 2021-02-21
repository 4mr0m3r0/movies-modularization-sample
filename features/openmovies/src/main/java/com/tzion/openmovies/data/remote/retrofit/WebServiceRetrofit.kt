package com.tzion.openmovies.data.remote.retrofit

import com.tzion.openmovies.data.remote.model.RemoteSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceRetrofit {

    @GET("/")
    suspend fun getMovies(
        @Query("s") searchCriteria: String?,
        @Query("apikey") apiKey: String = "e818df3e"
    ): RemoteSearch

}