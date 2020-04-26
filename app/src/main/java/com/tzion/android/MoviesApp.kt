package com.tzion.android

import android.app.Application
import com.tzion.android.di.AppComponent
import com.tzion.android.di.DaggerAppComponent
import timber.log.Timber

class MoviesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent = DaggerAppComponent
        .factory()
        .create(applicationContext)

}