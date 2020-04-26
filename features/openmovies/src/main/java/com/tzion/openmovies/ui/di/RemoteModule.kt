package com.tzion.openmovies.ui.di

import com.tzion.remote.RetrofitWebServiceFactory
import com.tzion.openmovies.data.remote.RemoteImpl
import com.tzion.openmovies.data.remote.RetrofitWebService
import com.tzion.openmovies.data.remote.constant.WebServiceUrl
import com.tzion.openmovies.data.source.Remote
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