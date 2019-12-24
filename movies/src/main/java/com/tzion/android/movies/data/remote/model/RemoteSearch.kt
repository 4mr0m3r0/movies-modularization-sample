package com.tzion.android.movies.data.remote.model

import com.google.gson.annotations.SerializedName
import com.tzion.android.movies.data.remote.model.Constants.SEARCH

data class RemoteSearch(@SerializedName(SEARCH) val search: List<RemoteMovie>)