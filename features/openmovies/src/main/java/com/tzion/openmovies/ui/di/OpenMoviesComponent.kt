package com.tzion.openmovies.ui.di

import com.tzion.android.di.AppComponent
import com.tzion.dagger.FeatureScope
import com.tzion.openmovies.ui.FindMoviesActivity
import com.tzion.openmovies.ui.find.FindMoviesFragment
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [
        RemoteModule::class,
        DataModule::class,
        PresentationModule::class
    ], dependencies = [AppComponent::class]
)
interface OpenMoviesComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): OpenMoviesComponent
    }

    fun inject(fragment: FindMoviesFragment)

}