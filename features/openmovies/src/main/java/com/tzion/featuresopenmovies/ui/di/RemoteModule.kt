package com.tzion.featuresopenmovies.ui.di

import com.tzion.coreremote.RetrofitWebServiceFactory
import com.tzion.featuresopenmovies.data.remote.RemoteImpl
import com.tzion.featuresopenmovies.data.remote.RetrofitWebService
import com.tzion.featuresopenmovies.data.remote.constant.WebServiceUrl
import com.tzion.featuresopenmovies.data.source.Remote
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesRetrofitWebService(): RetrofitWebService =
            RetrofitWebServiceFactory<RetrofitWebService>().create(
                isDebug = true,
                tClass = RetrofitWebService::class.java,
                baseUrl = WebServiceUrl.BASE_URL
            )
    }

    @Binds
    abstract fun bindRemote(remoteImpl: RemoteImpl): Remote

}