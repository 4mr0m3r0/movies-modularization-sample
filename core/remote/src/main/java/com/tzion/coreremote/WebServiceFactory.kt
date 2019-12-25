package com.tzion.coreremote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class WebServiceFactory <T> {

    fun create(isDebug: Boolean, tClass: Class<T>, baseUrl: String): T {
        val okHttpClient = makeOkHttpClient(makeLogginInterceptor(isDebug))
        return create(okHttpClient, tClass, baseUrl)
    }

    private fun create(okHttpClient: OkHttpClient, tClass: Class<T>, baseUrl: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(tClass)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLogginInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val loggin = HttpLoggingInterceptor()
        loggin.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggin
    }


}