package com.home.cinema.data.di.modules

import com.home.cinema.data.DataApp
import com.home.cinema.data.repositories.OnBoardingSharedPrefsRepository
import com.home.cinema.domain.repositories.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOnBoardingRepository(): OnBoardingRepository =
        OnBoardingSharedPrefsRepository(DataApp.getContext())
}