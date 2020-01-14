package com.tzion.featuresopenmovies.ui.di

import com.tzion.featuresopenmovies.data.DataRepository
import com.tzion.featuresopenmovies.domain.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRepository(dataRepository: DataRepository): Repository

}