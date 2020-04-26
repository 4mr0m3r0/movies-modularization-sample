package com.tzion.openmovies.ui.di

import com.tzion.remote.RetrofitWebServiceFactory
import com.tzion.openmovies.data.remote.RemoteImpl
import com.tzion.openmovies.data.remote.retrofit.WebServiceRetrofit
import com.tzion.openmovies.data.remote.config.WebServiceConfig
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
        fun providesRetrofitWebService(): WebServiceRetrofit =
            RetrofitWebServiceFactory<WebServiceRetrofit>().create(
                isDebug = true,
                tClass = WebServiceRetrofit::class.java,
                baseUrl = WebServiceConfig.BASE_URL
            )
    }

    @Binds
    abstract fun bindRemote(remoteImpl: RemoteImpl): Remote

}