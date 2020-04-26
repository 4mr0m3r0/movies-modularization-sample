package com.tzion.openmovies.data.source

import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val remote: Remote) {

    fun getRemote() = remote

}