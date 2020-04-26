package com.tzion.openmovies.ui.di

import com.tzion.openmovies.data.DataRepository
import com.tzion.openmovies.domain.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRepository(dataRepository: DataRepository): Repository

}