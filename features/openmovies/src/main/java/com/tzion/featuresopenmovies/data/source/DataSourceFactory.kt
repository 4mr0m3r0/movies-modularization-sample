package com.tzion.featuresopenmovies.data.source

import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val remote: Remote) {

    fun getRemote() = remote

}