package com.tzion.openmovies.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tzion.dagger.ViewModelFactory
import com.tzion.dagger.ViewModelKey
import com.tzion.mvi.execution.ExecutionThread
import com.tzion.mvi.execution.ExecutionThreadEnvironment
import com.tzion.mvi.execution.ExecutionThreadFactory
import com.tzion.openmovies.presentation.FindMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(FindMoviesViewModel::class)
    abstract fun bindsFindMoviesViewModel(findMoviesViewModel: FindMoviesViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideExecutionThread(): ExecutionThread = ExecutionThreadFactory.makeExecutionThread(
            ExecutionThreadEnvironment.APPLICATION
        )
    }

}