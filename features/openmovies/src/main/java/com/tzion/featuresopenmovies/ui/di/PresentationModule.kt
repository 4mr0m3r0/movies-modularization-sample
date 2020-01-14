package com.tzion.featuresopenmovies.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tzion.coredagger.ViewModelFactory
import com.tzion.coredagger.ViewModelKey
import com.tzion.featuresopenmovies.presentation.FindMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(FindMoviesViewModel::class)
    abstract fun bindsFindMoviesViewModel(findMoviesViewModel: FindMoviesViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}