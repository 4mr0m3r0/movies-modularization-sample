package com.tzion.android.movies.data.source

import com.tzion.android.movies.data.repository.Remote
import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val remote: Remote) {

    fun getRemote() = remote

}