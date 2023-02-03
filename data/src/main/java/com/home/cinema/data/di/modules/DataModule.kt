package com.home.cinema.data.di.modules

import com.home.cinema.data.page.home.HomePremiersRepository
import com.home.cinema.data.page.onboarding.OnBoardingSharedPrefsRepository
import com.home.cinema.domain.repositories.page.home.PremiersRepository
import com.home.cinema.domain.repositories.page.onboarding.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOnBoardingRepository(): OnBoardingRepository = OnBoardingSharedPrefsRepository()

    @Provides
    fun providePremiersRepository(): PremiersRepository = HomePremiersRepository()
}