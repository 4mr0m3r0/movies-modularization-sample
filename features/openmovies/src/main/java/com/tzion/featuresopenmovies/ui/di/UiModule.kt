package com.tzion.featuresopenmovies.ui.di

import android.content.Context
import com.tzion.featuresopenmovies.ui.FindMoviesActivity
import dagger.Module
import dagger.Provides

@Module
class UiModule(private val context: Context) {

    @Provides
    fun providesContext(): Context = context

}