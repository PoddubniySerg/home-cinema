package com.home.cinema.data.di.modules

import com.home.cinema.data.repositories.page.home.HomePopularRepository
import com.home.cinema.data.repositories.page.home.HomePremiersRepository
import com.home.cinema.data.repositories.page.home.HomeTVSeriesRepositoryImpl
import com.home.cinema.data.repositories.page.home.HomeTop250RepositoryImpl
import com.home.cinema.data.repositories.page.onboarding.OnBoardingSharedPrefsRepository
import com.home.cinema.domain.repositories.page.home.HomeTVSeriesRepository
import com.home.cinema.domain.repositories.page.home.HomeTop250Repository
import com.home.cinema.domain.repositories.page.home.PopularRepository
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

    @Provides
    fun providePopularRepository(): PopularRepository = HomePopularRepository()

    @Provides
    fun provideHomeTop250Repository(): HomeTop250Repository = HomeTop250RepositoryImpl()

    @Provides
    fun provideHomeTVSeriesRepository(): HomeTVSeriesRepository = HomeTVSeriesRepositoryImpl()
}