package com.tzion.featuresopenmovies.ui.di

import com.tzion.featuresopenmovies.ui.FindMoviesActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RemoteModule::class,
        DataModule::class,
        PresentationModule::class])
interface OpenMoviesComponent {

    @Component.Builder
    interface Builder {

        fun build(): OpenMoviesComponent

        @BindsInstance fun activity(activity: FindMoviesActivity): Builder

    }

}