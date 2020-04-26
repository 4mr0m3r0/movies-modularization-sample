package com.tzion.openmovies.ui.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class UiModule(private val context: Context) {

    @Provides
    fun providesContext(): Context = context

}